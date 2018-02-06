package com.mmall.practice.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@ThreadSafe
public class ImmutableExample5 {

    private final static List<Integer> set = ImmutableList.of(1, 2, 3);

    private final static ImmutableList<Integer> list = ImmutableList.copyOf(set);

    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.<Integer, Integer>builder().put(1, 2).build();

    public static void main(String[] args) {
        set.add(4);
        list.add(1);
        map.put(1,2);
    }

}
