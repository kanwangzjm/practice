package com.mmall.practice.example.cache;

import com.mmall.practice.common.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/put")
    @ResponseBody
    public JsonData put(@RequestParam("k") String k, @RequestParam("v") String v) throws Exception {
        redisClient.set(k, v);
        return JsonData.success();
    }

    @RequestMapping("/get")
    @ResponseBody
    public JsonData get(@RequestParam("k") String k) throws Exception {
        return JsonData.success(redisClient.get(k));
    }
}
