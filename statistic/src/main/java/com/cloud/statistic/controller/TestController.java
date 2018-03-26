package com.cloud.statistic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test
 *
 * @author 7le
 * @since v1.0.0
 */
@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/status")
    public String health() {
        return "Winter is coming";
    }

    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

    public static void main(String[] args) {
        String str="abc";
        String str1=new String("abc");
        System.out.println(str.toString());
        System.out.println(str1.toString());
    }

    class A {

        private String str;
    }
}
