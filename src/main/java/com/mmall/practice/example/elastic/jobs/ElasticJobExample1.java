package com.mmall.practice.example.elastic.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticJobExample1 implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("ElasticJobExample1 execute");
    }
}
