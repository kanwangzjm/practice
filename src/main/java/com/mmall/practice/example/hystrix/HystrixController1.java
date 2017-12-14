package com.mmall.practice.example.hystrix;

import com.mmall.practice.common.JsonData;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-javanica#configuration
 */
@Slf4j
@Controller
@RequestMapping("/hystrix1")
@DefaultProperties(defaultFallback = "fallback")
public class HystrixController1 {

    @HystrixCommand(fallbackMethod = "fail1")
    @RequestMapping("/test1")
    @ResponseBody
    public JsonData test1() {
        throw new RuntimeException();
    }

    private JsonData fail1() {
        log.warn("fail1");
        return JsonData.fail("fail1");
    }

    @HystrixCommand(fallbackMethod = "fail2")
    @RequestMapping("/test2")
    @ResponseBody
    public JsonData test2() {
        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fail3")
    private JsonData fail2() {
        log.warn("fail2");
        throw new RuntimeException();
    }

    @HystrixCommand
    private JsonData fail3() {
        log.warn("fail3");
        throw new RuntimeException();
    }

    private JsonData fallback() {
        log.warn("fallback");
        return JsonData.fail("fallback");
    }
}
