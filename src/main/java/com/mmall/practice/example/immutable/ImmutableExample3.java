package com.mmall.practice.example.immutable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

@Slf4j
@ThreadSafe
public class ImmutableExample3 {

    private static Map<Integer, Integer> map = ImmutableMap.of(1, 2, 3, 4, 5, 6);

    public static void main(String[] args) {
        map.put(1, 4);
        log.info(map.get(1).toString());
    }
}
