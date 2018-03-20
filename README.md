# practice
java concurrency && high concurrency

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


## docker

mvn clean package docker:build