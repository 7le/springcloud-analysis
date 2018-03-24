package com.cloud.ad.web;

import com.cloud.ad.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdController
 *
 * @author : 7le
 * @since v1.0.0
 *
 */
@RestController
public class AdController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return testService.hiService(name);
    }

}