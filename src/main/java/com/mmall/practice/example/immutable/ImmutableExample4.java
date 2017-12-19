package com.mmall.practice.example.immutable;

import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@ThreadSafe
public class ImmutableExample4 {

    private static final String flag1 = "1";
    private static final Integer flag2 = 1;
    private static final BigDecimal flag3 = BigDecimal.ONE;
    private static final Integer flag4 = 1000;

    public static void main(String[] args) {
        update(flag1, flag2, flag3, flag4);
        log.info("{},{},{},{}", flag1, flag2, flag3, flag4);
    }

    public static void update(String test1, Integer test2, BigDecimal test3, Integer test4) {
        test1 = "2";
        test2 = 2;
        test3 = BigDecimal.TEN;
        test4 = 2000;
        log.info("{},{},{},{}", flag1, flag2, flag3, flag4);
    }
}
