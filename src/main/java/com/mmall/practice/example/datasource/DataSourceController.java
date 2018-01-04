package com.mmall.practice.example.datasource;

import com.mmall.practice.common.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping("/dataSource")
public class DataSourceController {

    @Resource
    private DataSourceRoutingService dataSourceRoutingService;

    @RequestMapping("/test1")
    @ResponseBody
    public JsonData test1(@RequestParam("id") int id) {
        return JsonData.success(dataSourceRoutingService.list1(id));
    }

    @RequestMapping("/test2")
    @ResponseBody
    public JsonData test2(@RequestParam("id") int id) {
        return JsonData.success(dataSourceRoutingService.list2(id));
    }

    @RequestMapping("/test3")
    @ResponseBody
    public JsonData test3(@RequestParam("id") int id) {
        return JsonData.success(dataSourceRoutingService.list3(id));
    }

    @RequestMapping("/test4")
    @ResponseBody
    public JsonData test4(@RequestParam("id") int id) {
        return JsonData.success(dataSourceRoutingService.list4(id));
    }

    @RequestMapping("/test5")
    @ResponseBody
    public JsonData test5(@RequestParam("id") int id) {
        return JsonData.success(dataSourceRoutingService.list5(id));
    }
}
