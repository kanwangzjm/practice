package com.mmall.practice.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

@Slf4j
@ThreadSafe
public class ImmutableExample2 {

    private static Map<Integer, Integer> map;

    static {
        map = Maps.newHashMap();
        map.put(1, 2);
        map.put(2, 4);
        map.put(3, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 4);
        log.info(map.get(1).toString());
    }
}
