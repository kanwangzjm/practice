package com.mmall.practice.example.canel;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.MDC;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class AbstractCanalClientTest {


    protected static final String             SEP                = SystemUtils.LINE_SEPARATOR;
    protected static final String             DATE_FORMAT        = "yyyy-MM-dd HH:mm:ss";
    protected volatile boolean                running            = false;
    protected Thread.UncaughtExceptionHandler handler            = new Thread.UncaughtExceptionHandler() {

        public void uncaughtException(Thread t, Throwable e) {
            log.error("parse events has an error", e);
        }
    };
    protected Thread                          thread             = null;
    protected CanalConnector                  connector;
    protected static String                   context_format     = null;
    protected static String                   row_format         = null;
    protected static String                   transaction_format = null;
    protected String                          destination;

    static {
        context_format = SEP + "****************************************************" + SEP;
        context_format += "* Batch Id: [{}] ,count : [{}] , memsize : [{}] , Time : {}" + SEP;
        context_format += "* Start : [{}] " + SEP;
        context_format += "* End : [{}] " + SEP;
        context_format += "****************************************************" + SEP;

        row_format = SEP
                + "----------------> binlog[{}:{}] , name[{},{}] , eventType : {} , executeTime : {}({}) , gtid : ({}) , delay : {} ms"
                + SEP;

        transaction_format = SEP
                + "================> binlog[{}:{}] , executeTime : {}({}) , gtid : ({}) , delay : {}ms"
                + SEP;

    }

    public AbstractCanalClientTest(String destination){
        this(destination, null);
    }

    public AbstractCanalClientTest(String destination, CanalConnector connector){
        this.destination = destination;
        this.connector = connector;
    }

    protected void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(new Runnable() {

            public void run() {
                process();
            }
        });

        thread.setUncaughtExceptionHandler(handler);
        running = true;
        thread.start();
    }

    protected void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }

        MDC.remove("destination");
    }

    protected void process() {
        int batchSize = 5 * 1024;
        while (running) {
            try {
                MDC.put("destination", destination);
                connector.connect();
                connector.subscribe();
                connector.rollback();
                while (running) {
                    Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        // try {
                        // Thread.sleep(1000);
                        // } catch (InterruptedException e) {
                        // }
                    } else {
                        printSummary(message, batchId, size);
                        printEntry(message.getEntries());
                    }

                    connector.ack(batchId); // 提交确认
                    // connector.rollback(batchId); // 处理失败, 回滚数据
                }
            } catch (Exception e) {
                log.error("process error!", e);
            } finally {
                connector.disconnect();
                MDC.remove("destination");
            }
        }
    }

    private void printSummary(Message message, long batchId, int size) {
        long memsize = 0;
        for (CanalEntry.Entry entry : message.getEntries()) {
            memsize += entry.getHeader().getEventLength();
        }

        String startPosition = null;
        String endPosition = null;
        if (!CollectionUtils.isEmpty(message.getEntries())) {
            startPosition = buildPositionForDump(message.getEntries().get(0));
            endPosition = buildPositionForDump(message.getEntries().get(message.getEntries().size() - 1));
        }

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        log.info(context_format, new Object[] { batchId, size, memsize, format.format(new Date()), startPosition,
                endPosition });
    }

    protected String buildPositionForDump(CanalEntry.Entry entry) {
        long time = entry.getHeader().getExecuteTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String position = entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
                + entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
        if (StringUtils.isNotEmpty(entry.getHeader().getGtid())) {
            position += " gtid(" + entry.getHeader().getGtid() + ")";
        }
        return position;
    }

    protected void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            long executeTime = entry.getHeader().getExecuteTime();
            long delayTime = new Date().getTime() - executeTime;
            Date date = new Date(entry.getHeader().getExecuteTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN) {
                    CanalEntry.TransactionBegin begin = null;
                    try {
                        begin = CanalEntry.TransactionBegin.parseFrom(entry.getStoreValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                    }
                    // 打印事务头信息，执行的线程id，事务耗时
                    log.info(transaction_format,
                            new Object[] { entry.getHeader().getLogfileName(),
                                    String.valueOf(entry.getHeader().getLogfileOffset()),
                                    String.valueOf(entry.getHeader().getExecuteTime()), simpleDateFormat.format(date),
                                    entry.getHeader().getGtid(), String.valueOf(delayTime) });
                    log.info(" BEGIN ----> Thread id: {}", begin.getThreadId());
                    printXAInfo(begin.getPropsList());
                } else if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                    CanalEntry.TransactionEnd end = null;
                    try {
                        end = CanalEntry.TransactionEnd.parseFrom(entry.getStoreValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                    }
                    // 打印事务提交信息，事务id
                    log.info("----------------\n");
                    log.info(" END ----> transaction id: {}", end.getTransactionId());
                    printXAInfo(end.getPropsList());
                    log.info(transaction_format,
                            new Object[] { entry.getHeader().getLogfileName(),
                                    String.valueOf(entry.getHeader().getLogfileOffset()),
                                    String.valueOf(entry.getHeader().getExecuteTime()), simpleDateFormat.format(date),
                                    entry.getHeader().getGtid(), String.valueOf(delayTime) });
                }

                continue;
            }

            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                CanalEntry.RowChange rowChage = null;
                try {
                    rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                }

                CanalEntry.EventType eventType = rowChage.getEventType();

                log.info(row_format,
                        new Object[] { entry.getHeader().getLogfileName(),
                                String.valueOf(entry.getHeader().getLogfileOffset()), entry.getHeader().getSchemaName(),
                                entry.getHeader().getTableName(), eventType,
                                String.valueOf(entry.getHeader().getExecuteTime()), simpleDateFormat.format(date),
                                entry.getHeader().getGtid(), String.valueOf(delayTime) });

                if (eventType == CanalEntry.EventType.QUERY || rowChage.getIsDdl()) {
                    log.info(" sql ----> " + rowChage.getSql() + SEP);
                    continue;
                }

                printXAInfo(rowChage.getPropsList());
                for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        printColumn(rowData.getAfterColumnsList());
                    } else {
                        printColumn(rowData.getAfterColumnsList());
                    }
                }
            }
        }
    }

    protected void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            StringBuilder builder = new StringBuilder();
            try {
                if (StringUtils.containsIgnoreCase(column.getMysqlType(), "BLOB")
                        || StringUtils.containsIgnoreCase(column.getMysqlType(), "BINARY")) {
                    // get value bytes
                    builder.append(column.getName() + " : "
                            + new String(column.getValue().getBytes("ISO-8859-1"), "UTF-8"));
                } else {
                    builder.append(column.getName() + " : " + column.getValue());
                }
            } catch (UnsupportedEncodingException e) {
            }
            builder.append("    type=" + column.getMysqlType());
            if (column.getUpdated()) {
                builder.append("    update=" + column.getUpdated());
            }
            builder.append(SEP);
            log.info(builder.toString());
        }
    }

    protected void printXAInfo(List<CanalEntry.Pair> pairs) {
        if (pairs == null) {
            return;
        }

        String xaType = null;
        String xaXid = null;
        for (CanalEntry.Pair pair : pairs) {
            String key = pair.getKey();
            if (StringUtils.endsWithIgnoreCase(key, "XA_TYPE")) {
                xaType = pair.getValue();
            } else if (StringUtils.endsWithIgnoreCase(key, "XA_XID")) {
                xaXid = pair.getValue();
            }
        }

        if (xaType != null && xaXid != null) {
            log.info(" ------> " + xaType + " " + xaXid);
        }
    }

    public void setConnector(CanalConnector connector) {
        this.connector = connector;
    }

    /**
     * 获取当前Entry的 GTID信息示例
     * @param header
     * @return
     */
    public static String getCurrentGtid(CanalEntry.Header header) {
        List<CanalEntry.Pair> props = header.getPropsList();
        if (props != null && props.size() > 0) {
            for (CanalEntry.Pair pair : props) {
                if ("curtGtid".equals(pair.getKey())) {
                    return pair.getValue();
                }
            }
        }
        return "";
    }

    /**
     * 获取当前Entry的 GTID Sequence No信息示例
     * @param header
     * @return
     */
    public static String getCurrentGtidSn(CanalEntry.Header header) {
        List<CanalEntry.Pair> props = header.getPropsList();
        if (props != null && props.size() > 0) {
            for (CanalEntry.Pair pair : props) {
                if ("curtGtidSn".equals(pair.getKey())) {
                    return pair.getValue();
                }
            }
        }
        return "";
    }

    /**
     * 获取当前Entry的 GTID Last Committed信息示例
     * @param header
     * @return
     */
    public static String getCurrentGtidLct(CanalEntry.Header header) {
        List<CanalEntry.Pair> props = header.getPropsList();
        if (props != null && props.size() > 0) {
            for (CanalEntry.Pair pair : props) {
                if ("curtGtidLct".equals(pair.getKey())) {
                    return pair.getValue();
                }
            }
        }
        return "";
    }
}
