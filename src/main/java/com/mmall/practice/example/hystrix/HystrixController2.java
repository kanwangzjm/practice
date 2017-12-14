package com.mmall.practice.example.hystrix;

import com.mmall.practice.common.JsonData;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-javanica#configuration
 */
@Slf4j
@Controller
@RequestMapping("/hystrix2")
@DefaultProperties(defaultFallback = "fallback")
public class HystrixController2 {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    @RequestMapping("/test1")
    @ResponseBody
    public JsonData test1() throws Exception {
        Thread.sleep(1000);
        return JsonData.success("test1");
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            })
    @RequestMapping("/test2")
    @ResponseBody
    public JsonData test2() throws Exception {
        Thread.sleep(1000);
        return JsonData.success("test1");
    }

    private JsonData fallback() {
        log.warn("fallback");
        return JsonData.fail("fallback");
    }
}
