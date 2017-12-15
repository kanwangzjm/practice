package com.mmall.practice.example.threadLocal;

import com.mmall.practice.common.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/threadLocal")
@Slf4j
public class ThreadLocalController {

    @RequestMapping("/test1")
    @ResponseBody
    public JsonData test1() {
        log.info("thread id:{}", Thread.currentThread().getId());
        return JsonData.success(RequestHolder.getId());
    }
}
