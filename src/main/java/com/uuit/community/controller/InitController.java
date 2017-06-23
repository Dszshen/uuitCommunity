package com.uuit.community.controller;

import com.uuit.community.service.user.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 *
 * @author zhangbin
 * 登陆接口
 *
 */
@Controller
public class InitController {

    @Resource
    private UserService userService;

}
