package com.uuit.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.uuit.community.common.util.JsonResult;
import com.uuit.community.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *
 * @author zhangbin
 * 登陆接口
 *
 */
@Controller
public class LoginController {

    @Resource
    private UserService userService;

    /**
     *
     * @return 登陆静态页面
     */
    @RequestMapping("login.html")
    public String loginPage(){
        return "login";
    }

    /**
     * 登陆系统
     * @param loginInfo
     * @return 返回登陆信息
     */
    @RequestMapping(method = RequestMethod.POST,value="login")
    @ResponseBody
    public JsonResult<JSONObject> login(@RequestBody JSONObject loginInfo){
        return userService.login(loginInfo);
    }
}
