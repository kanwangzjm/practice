package com.mmall.practice.example.canel;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class SimpleCanalClientTest extends AbstractCanalClientTest {

    public SimpleCanalClientTest(String destination){
        super(destination);
    }

    public static void main(String args[]) {
        // 根据ip，直接创建链接，无HA的功能
        String destination = "example";
        String ip = AddressUtils.getHostIp();
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(ip, 11111),
                destination,
                "",
                "");

        final SimpleCanalClientTest clientTest = new SimpleCanalClientTest(destination);
        clientTest.setConnector(connector);
        clientTest.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                try {
                    log.info("## stop the canal client");
                    clientTest.stop();
                } catch (Throwable e) {
                    log.warn("##something goes wrong when stopping canal:", e);
                } finally {
                    log.info("## canal client is down.");
                }
            }

        });
    }

}