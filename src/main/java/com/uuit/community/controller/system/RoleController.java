package com.uuit.community.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.uuit.community.bean.Role;
import com.uuit.community.common.util.JsonResult;
import com.uuit.community.common.util.JsonobjectToBean;
import com.uuit.community.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangbin on 2016/6/26.
 * 角色管理
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    public RoleService roleService;

    /**
     * 根据id获取角色信息
     * @param roleId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public JsonResult get(@RequestParam String roleId){
        return JsonResult.success(roleService.getRoleById(roleId));
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="add")
    public JsonResult add(@RequestBody JSONObject role){
        try {
            Role role1 = (Role) JsonobjectToBean.fromJsonToBean(role,Role.class);
            return JsonResult.success(roleService.addRole(role1));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure("json转java类失败！");
        }
        //roleService.addRole(role);
        //return JsonResult.failure("程序出错！");
    }

    /**
     * 获取角色列表
     * @return
     */
    @RequestMapping("list")
    public JsonResult<List<Role>> list(){
        return roleService.list();
    }

    /**
     * 角色分组后的分组角色信息
     * @param forbid 是否包含禁用的角色信息（no/yes）
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value="rolesGroupList")
    public JsonResult rolesGroupList(@RequestParam(required = false) String forbid){
        return roleService.getGroupRoles(forbid);
    }

    /**
     * 获取角色分组信息
     * @return
     */
    @RequestMapping("getRoleGroups")
    public JsonResult getRoleGroups(){
        return roleService.getRoleGroups();
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @RequestMapping("update")
    public JsonResult updateRole(@RequestBody JSONObject role){
        return roleService.updateRole(role);
    }

    /**
     * 获取用户角色列表
     * @param params
     * @return
     */
    @RequestMapping("getRolesOfUser")
    public JsonResult getRolesOfUser(@RequestParam Map<String,String> params){
        String userId = params.get("userId");
        return JsonResult.success(roleService.getRolesOfUser(userId));
    }

    /**
     * 为用户添加角色
     * @param params
     * @return
     */
    @RequestMapping("addRoleOfUser")
    public JsonResult addRoleOfUser(@RequestBody JSONObject params){
        String userId = params.getString("userId");
        String roleIds = params.getString("roleIds");
        return JsonResult.success(roleService.addRoleToUser(userId,roleIds,null,null));
    }

    /**
     * 更新用户角色信息
     * @param params
     * @return
     */
    @RequestMapping("updateRoleOfUser")
    public JsonResult updateRoleOfUser(@RequestBody JSONObject params){
        String userId = params.getString("userId");
        String rolesIds = params.getString("rolesIds");
        String rolesGroup = params.getString("rolesGroup");
        //String roleIds = rolesIds.toString();
        return JsonResult.success(roleService.updateRoleOfUser(userId,rolesIds,0,rolesGroup));
    }

}
