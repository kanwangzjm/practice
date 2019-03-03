# 一、数据库配置

## 1、开启 binlog

show variables like 'binlog_format'; 为 on 代表开启

可以通过set sql_log_bin=1; 命令来启用

## 2、binlog format 为 row

show variables like 'binlog_format'; 为row才可以

可以通过  set global binlog_format='ROW'; 修改

## 3、binlog_checksum 为 none

show global variables like 'binlog_checksum'; 为none 才可以

可以通过 set global binlog_checksum=none; 修改

## 4、创建数据库、表及用户

代码里要配置

databus example里也有，可以之后配置

# 二、下载源码及修改

## 1、下载源码

git clone https://github.com/linkedin/databus.git

## 2、修改代码

1）在databus 主目录下的build.gradle 添加如下代码

```java
tasks.withType(JavaCompile) {
    options.encoding = "UTF-8"
}
    
tasks.withType(GroovyCompile) {
    groovyOptions.encoding = "MacRoman"
}
```

2）databus主目录subprojects.gradle 177行修改为
 
 ```java
 if (JavaVersion.current().isJava8Compatible()) {
   allprojects {
     tasks.withType(Javadoc) {
       options.addStringOption('Xdoclint:none', '-quiet')
       options.encoding("UTF-8")
       options.charSet("UTF-8")
     }
   }
 }
 ```

3）下载ojdbc6.jar

copy ojdbc6.jar 到sandbox-repo/com/oracle/ojdbc6/11.2.0.2.0 下

重命名ojdbc6.jar为ojdbc6-11.2.0.2.0.jar


# 三、配置 Relay

## 1、配置Relay属性文件

databus2-example-relay-pkg/conf/relay-or-person.properties

包括端口号，buffer存储策略，maxScn存放地址等信息（不调整也ok）

## 2、配置被监控表source信息

databus2-example-relay-pkg/conf/sources-or-person.json

URI format:mysql://username/password@mysql_host[:mysql_port]/mysql_serverid/binlog_prefix

username：数据库用户

password：数据库用户的密码

mysql_host：数据库地址

mysql_port：数据库端口

mysql_serverid：通过 show variables like '%server_id%'; 获取

binlog_prefix：通过 show master status\G; 获取，如果 File 值为 mysql-bin.000009， 那么 binlog_prefix 为 mysql-bin

@：需要转义成 %2F

例子为：

mysql://root%2F466420182@localhost:3306/1/or_test

## 3、配置被监控表的Avro schema文件 

databus2-example-relay-pkg/schemas_registry/

com.linkedin.events.example.or_test.Person.1.avsc

## 4、注册Avro schema到index.schemas_registry文件

databus2-example-relay-pkg/schemas_registry/index.schemas_registry

文件中添加行

com.linkedin.events.example.or_test.Person.1.avsc 

# 四、配置 Client

## 1、配置Client属性文件

databus2-example-client-pkg/conf/client-person.properties

包括端口号，buffer存储策略，checkpoint持久化等信息（不调整也ok）

## 2、Client 中消费检查

databus2-example-client/src/main/java下的PersonConsumer

## 3、Client 中启动主类检查

databus2-example-client/src/main/java下的PersonClient类

# 五、Build-启动-测试

## 1、Build

进入databus根目录执行命令 gradle -Dopen_source=true assemble 即可完成build

成功后在databus根目录下生成名为build的文件夹

可能需要配置 gradle环境，这里就不细说了

## 2、启动Relay

1）cd build/databus2-example-relay-pkg/distributions

2）tar -zxvf databus2-example-relay-pkg.tar.gz解压

3）执行启动脚本 ./bin/start-example-relay.sh or_person -Y ./conf/sources-or-person.json

4）执行命令 curl -s http://localhost:11115/sources 

返回如下内容说明启动成功： [{"name":"com.linkedin.events.example.or_test.Person","id":40}]

## 3、启动Client

1）cd build/databus2-example-client-pkg/distributions

2）tar -zxvf databus2-example-client-pkg.tar.gz解压

3）执行启动脚本 ./bin/start-example-client.sh person

4）执行命令 curl http://localhost:11115/relayStats/outbound/http/clients 

返回如下内容说明启动成功： ["localhost"]

## 4、测试

Relay和Client启动成功后，就已经开始对person表进行数据变更捕获了，现在向person表插入一条记录

databus2-example-relay-pkg/distributions/logs下的relay.log有相应记录

databus2-example-client-pkg/distributions/logs下的client.log有相应记录

有时需要执行 rmiregistry

# 六、参考

https://sq.163yun.com/blog/article/173552201158811648

https://sq.163yun.com/blog/article/173554725500456960

https://github.com/linkedin/databus

https://dev.mysql.com/doc/refman/5.5/en/mysqlbinlog.html






