package com.mmall.practice.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.practice.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@NotThreadSafe
public class ImmutableExample1 {

    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(2, 4);
        map.put(3, 6);
    }

    public static void main(String[] args) {
        map.put(1, 4);
        log.info(map.get(1).toString());
    }
}
