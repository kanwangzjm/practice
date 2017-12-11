package com.mmall.practice.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/count")
public class CountController {

    private int count = 0;

    @RequestMapping("/incr")
    @ResponseBody
    public Integer incr() {
        count++;
        return count;
    }
}
