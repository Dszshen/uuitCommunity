package com.uuit.community.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangbin
 * If a html page is just a static page, then you can define it here.
 */
@Controller
public class HtmlController {

    private static final Logger LOGGER = Logger.getLogger(HtmlController.class);
    //@RequestMapping(value={"**.html","/**/*.html"})
    @RequestMapping("/**/**.html")
    public void html() {
    }

    @RequestMapping("/")
    public String index() {
        return "front/index";
    }
}

