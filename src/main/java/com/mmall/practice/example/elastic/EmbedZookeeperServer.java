package com.mmall.practice.example.elastic;

import org.apache.curator.test.TestingServer;

import java.io.File;
import java.io.IOException;

public class EmbedZookeeperServer {
    private static TestingServer testingServer;

    public static void start(final int port){
        try{
            testingServer = new TestingServer(port,new File(String.format("target/test_zk_data/%s/",System.nanoTime())));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                    testingServer.close();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
