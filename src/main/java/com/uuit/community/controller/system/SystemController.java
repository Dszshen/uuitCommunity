package com.uuit.community.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.uuit.community.common.util.Constant;
import com.uuit.community.common.util.JsonResult;
import com.uuit.community.service.system.SystemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by zhangbin on 2016/6/26.
 * 系统管理
 */
@RestController
@RequestMapping("sys")
public class SystemController {

    @Resource
    private SystemService systemService;

    /**
     * 查询配置
     *
     * @param type 请求路径
     * @param id   查询参数
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "{type:base|security|email}")
    public JsonResult configGet(@PathVariable String type, @RequestParam("id") String id) {
        JSONObject params = new JSONObject();
        params.put("id", id);
        return request("get", type, params);
    }

    /**
     * 添加配置信息
     *
     * @param type
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "{type:base|security|email}")
    public JsonResult configAdd(@PathVariable String type, @RequestBody JSONObject params) {
        return request("post", type, params);
    }

    /**
     * 配置更新
     *
     * @param type
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{type:base|security|email}")
    public JsonResult configUpdate(@PathVariable String type, @RequestBody JSONObject params) {
        return request("put", type, params);
    }

    /**
     * @param reqType 请求方式
     * @param reqPath 请求路径
     * @param params  请求参数
     * @return 返回对应的数据
     */
    private JsonResult request(String reqType, String reqPath, JSONObject params) {

        String opType = Constant.DATABASE_OP_SELECT;
        if (reqType.equals("post")) {
            opType = Constant.DATABASE_OP_INSERT;
        } else if (reqType.equals("put")) {
            opType = Constant.DATABASE_OP_UPDATE;
        }

        if (StringUtils.isBlank(reqPath)) {
            return JsonResult.failure("对不起，请求路径错误！");
        } else if (reqPath.equals("base")) {
            return systemService.config(Constant.SYSTEM_CONFIG_BASE, params, opType);
        } else if (reqPath.equals("security")) {
            return systemService.config(Constant.SYSTEM_CONFIG_SECURITY, params, opType);
        } else if (reqPath.equals("email")) {
            return systemService.config(Constant.SYSTEM_CONFIG_EMAIL, params, opType);
        }

        return JsonResult.failure("对不起，请求路径错误！");
    }
}
