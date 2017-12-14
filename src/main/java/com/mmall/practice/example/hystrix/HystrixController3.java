package com.mmall.practice.example.hystrix;

import com.mmall.practice.common.JsonData;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-javanica#configuration
 */
@Slf4j
@Controller
@RequestMapping("/hystrix3")
@DefaultProperties(defaultFallback = "fallback")
public class HystrixController3 {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //  熔断器在整个统计时间内是否开启的阀值
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),  // 至少有3个请求才进行熔断错误比率计算
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), //当出错率超过50%后熔断器启动
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),  // 熔断器工作时间，超过这个时间，先放一个请求进去，成功的话就关闭熔断，失败就再等一段时间
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")// 统计滚动的时间窗口
    })
    @RequestMapping("/test1")
    @ResponseBody
    public JsonData test1(@RequestParam("id") Integer id) {
        log.info("id:{}", id);
        if (id % 2 == 0) {
            throw new RuntimeException();
        }
        return JsonData.success("test_" + id);
    }

    private JsonData fallback() {
        log.warn("fallback");
        return JsonData.fail("fallback");
    }
}
