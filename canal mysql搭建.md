# 下载canal

```
git clone git@github.com:alibaba/canal.git
cd canal; 
mvn clean install -Dmaven.test.skip -Denv=release
```
编译完成后，会在根目录下产生target/canal.deployer-$version.tar.gz

# 解压缩

```
mkdir /tmp/canal
tar zxvf canal.deployer-$version.tar.gz  -C /tmp/canal
```
解压完成后
```
cd /tmp/canal
```
# 配置修改

应用参数
```
vi conf/example/instance.properties

## mysql serverId， show variables like '%server_id%';
canal.instance.mysql.slaveId = 1234

#position info，需要改成自己的数据库信息
canal.instance.master.address = 127.0.0.1:3306
canal.instance.master.journal.name =
canal.instance.master.position =
canal.instance.master.timestamp =

#canal.instance.standby.address =
#canal.instance.standby.journal.name =
#canal.instance.standby.position =
#canal.instance.standby.timestamp =

#username/password，需要改成自己的数据库信息
canal.instance.dbUsername = canal
canal.instance.dbPassword = canal
canal.instance.defaultDatabaseName =
canal.instance.connectionCharset = UTF-8

#table regex
canal.instance.filter.regex = .\..
```
说明：

canal.instance.connectionCharset 代表数据库的编码方式对应到java中的编码类型，比如UTF-8，GBK , ISO-8859-1

如果系统是1个cpu，需要将canal.instance.parser.parallel设置为false

# 准备启动
```
sh bin/startup.sh
```
# 查看日志

```
 logs/canal/canal.log

 logs/example/example.log
```
# 关闭
```
sh bin/stop.sh
```