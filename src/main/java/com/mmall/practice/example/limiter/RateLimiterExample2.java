package com.mmall.practice.example.limiter;

import com.google.common.util.concurrent.RateLimiter;
import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ThreadSafe
public class RateLimiterExample2 {

    private static int clientTotal = 100;

    private static RateLimiter rateLimiter = RateLimiter.create(5);

    public static void main(String[] args) throws Exception {
        for (int index = 0; index < clientTotal; index++) {
            try {
                rateLimiter.acquire();
                handle(index);
            } catch (Exception e) {
                log.error("exception", e);
            }
        }
    }

    private static void handle(int i) throws Exception {
        log.info("{}", i);
    }
}
