package com.cloud.config.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author : 7le
 * @since v1.0.0
 */
@RestController
public class BaseController {

    @RequestMapping("/status")
    public String health() {
        return "Winter is coming";
    }

 }
