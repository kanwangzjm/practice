

## elastic job dashboard

项目内

cd elastic-job-lite-console-1.5/bin

sh start.sh

## hystrix dashboard

/hystrix

配置：http://hystrix-app:port/hystrix.stream 

## zookeeper

本地

cd /usr/local/kafka

bin/zookeeper-server-start.sh config/zookeeper.properties

## kafka

本地

cd /usr/local/kafka

bin/kafka-server-start.sh config/server.properties

## rabbitmq

本地

sudo rabbitmq-server

rabbitmqctl list_queues

http://127.0.0.1:15672/

安装延迟队列插件：

rabbitmq-plugins enable rabbitmq_delayed_message_exchange

（mac下 /usr/local/Cellar/rabbitmq/3.6.0/plugins，将下载的ez格式放到这个地址）

rabbit插件下载地址：

http://www.rabbitmq.com/community-plugins.html

rabbitmq安装说明：

http://www.rabbitmq.com/installing-plugins.html

## redis

本地

/usr/local/bin/redis-server 

/usr/local/bin/redis-cli

## docker

mvn clean package docker:build
