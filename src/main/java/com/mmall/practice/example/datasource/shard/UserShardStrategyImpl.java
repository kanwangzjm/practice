package com.mmall.practice.example.datasource.shard;

import com.google.code.shardbatis.strategy.ShardStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserShardStrategyImpl implements ShardStrategy {

    private final static int tableCount = 5;
    @Override
    public String getTargetTableName(String baseTableName, Object params, String mapperId) {
        // TODO: need change by fact
        int value = 2;
        try {
            int index = value % tableCount + 1;
            String strIndex;
            if(index < 10) {
                strIndex = "0" + index;
            } else {
                strIndex = "" + index;
            }
            return baseTableName + "_" + strIndex;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
