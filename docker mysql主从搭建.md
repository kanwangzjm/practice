
# 一、准备两台 mysql 服务器

## 1、搭建 Master

```
sudo docker run --name mysql_master -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
```
通过镜像 mysql:5.7 启动一个名为 mysql_master 的 MySQL 服务器，端口号是3306，映射的宿主机端口号是3306，root 账号密码是123456

## 2、搭建 Slave
```
sudo docker run --name mysql_slave -p 3307:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
```
如果重启过，运行需要使用: sudo docker run ${container_id}

## 3、查看对应Container ID
```
sudo docker ps -a | grep mysql
```

```
CONTAINER ID        IMAGE                           COMMAND                  CREATED             STATUS              PORTS                                         NAMES
4d39971c1432        daocloud.io/library/mysql:5.7   "docker-entrypoint.s…"   25 hours ago        Up 8 hours          3306/tcp, 33060/tcp, 0.0.0.0:3307->3307/tcp   mysql_master
c45eb2faf46a        daocloud.io/library/mysql:5.7   "docker-entrypoint.s…"   26 hours ago        Up 8 hours          3306/tcp, 33060/tcp, 0.0.0.0:3308->3307/tcp   mysql_slave
```

## 4、查看对应的ip

```
~  ᐅ sudo docker inspect --format='{{.NetworkSettings.IPAddress}}' 4d39971c1432
192.168.1.1
~  ᐅ sudo docker inspect --format='{{.NetworkSettings.IPAddress}}' c45eb2faf46a
192.168.1.2
```
Master 服务器地址是 192.168.1.1 ，Slave 服务器地址是 192.168.1.2

# 二、配置 Master 服务器

## 1、进入 Master 服务器
```
sudo docker exec -it 4d39971c1432 bash
```
4d39971c1432 为 master服务的 container_id

## 2、检查 mysql 状态
```
service mysql status
```
## 3、配置my.cnf

通常pwd为 /etc/mysql/my.cnf, 配置为(实际配置时去掉注释，并看一下是否需要调整)：
```
[mysqld]

server_id=100  // 设置server_id，一般设置为IP，同一局域网内注意要唯一

binlog-ignore-db=mysql  // 复制过滤：也就是指定哪个数据库不用同步（mysql库一般不同步）

log-bin=edu-mysql-bin  // 开启二进制日志功能，可以随便取，最好有含义（关键就是这里了）

binlog_cache_size=1M  // 为每个session 分配的内存，在事务过程中用来存储二进制日志的缓存

binlog_format=mixed  // 主从复制的格式（mixed,statement,row，默认格式是statement）

expire_logs_days=7  // 二进制日志自动删除/过期的天数。默认值为0，表示不自动删除。

slave_skip_errors=1062  //跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致

binlog-do-db=test1
```

## 4、配置完重启
```
service mysql restart
```
这个命令会使得容器停止，重新启动就可以了：docker start container_id

## 5、创建相关账户，并分配权限
```
mysql -uroot -p （密码每次额外输入），登录
```
```
CREATE USER 'slave'@'%' IDENTIFIED BY '123456';
GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'slave'@'%'; 
```
 
这里主要是要授予用户 slave REPLICATION SLAVE权限和REPLICATION CLIENT权限

# 三、配置 Slave 服务器 

## 1、进入 Master 服务器
```
sudo docker exec -it c45eb2faf46a bash
```
## 2、检查 mysql 状态
```
service mysql status
```
## 3、配置my.cnf
```
[mysqld]

server_id=101  // 设置server_id，一般设置为IP,注意要唯一

binlog-ignore-db=mysql  // 复制过滤：也就是指定哪个数据库不用同步（mysql库一般不同步）

log-bin=edu-mysql-slave1-bin  // 开启二进制日志功能，以备Slave作为其它Slave的Master时使用

binlog_cache_size=1M  // 为每个session 分配的内存，在事务过程中用来存储二进制日志的缓存

binlog_format=mixed  // 主从复制的格式（mixed,statement,row，默认格式是statement）

expire_logs_days=7  // 二进制日志自动删除/过期的天数。默认值为0，表示不自动删除。

slave_skip_errors=1062 // 跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致

relay_log=edu-mysql-relay-bin  // relay_log配置中继日志

log_slave_updates=1  // log_slave_updates表示slave将复制事件写进自己的二进制日志

read_only=1  // 防止改变数据(除了特殊的线程)

binlog-do-db=test1

replicate-do-db=test1
```
## 4、配置完重启
```
service mysql restart
```
# 四、完成Master和Slave链接

## 1、Master 核心信息准备
```
show master status\G
```
```
File: edu-mysql-bin.000006
Position: 154
Binlog_Do_DB: test1
Binlog_Ignore_DB: mysql
```
记录下 File 和 Position 字段的值，后面会用到

## 2、Slave 中 mysql 配置
```
change master to  master_host='192.168.1.1', master_user='slave', master_password='123456', master_port=3306, master_log_file='edu-mysql-bin.000006', master_log_pos=154, master_connect_retry=30;  
```
命令解释：

master_host: Master 的IP地址

master_user: 在 Master 中授权的用于数据同步的用户

master_password: 同步数据的用户的密码

master_port: Master 的数据库的端口号

master_log_file: 指定 Slave 从哪个日志文件开始复制数据，即上文中提到的 File 字段的值

master_log_pos: 从哪个 Position 开始读，即上文中提到的 Position 字段的值

master_connect_retry: 当重新建立主从连接时，如果连接失败，重试的时间间隔，单位是秒，默认是60秒。

## 3、Slave 开启主从同步状态
```
start slave

（终止的话为  stop slave）
```

## 4、确认主动同步状态
```
show slave status\G
```
# 五、验证

测试方法比较多，可以在 Master 中增加一个数据库，然后去 Slave 中查看是否同步过来了

如果没有成功，请仔细检查配置文件和配置过程

# 补充

1、如果想使用 vim ，而且docker里还不支持，安装：
```
apt-get update

apt-get install vim
```
2、如果要控制docker中mysql ip

1） docker客户端：Preferences - Daemon - Advanced, 增加

{ "bip" : "192.168.1.100/24" } 保证总体为json格式

2）docker内部：

~/.docker/daemon.json 里增加

{ "bip" : "192.168.1.100/24" } 保证总体为json格式

3、相关命令里sudo根据情况



