package com.uuit.community.service.system;

import com.alibaba.fastjson.JSONObject;
import com.uuit.community.bean.Role;
import com.uuit.community.common.util.JsonResult;
import com.uuit.community.dao.RoleDao;
import com.uuit.community.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zhangbin on 2016/6/15.
 */
@Service
public class RoleService extends BaseService {

    @Autowired
    public RoleDao roleDao;

    /**
     * 获取所有的role
     * @return
     */
    public JsonResult<List<Role>> list(){
        List<Role> list = roleDao.findAll();

        JsonResult<List<Role>> jsonResult = JsonResult.success(list);
        return  jsonResult;
    }

    /**
     * 按照角色组获取分组角色
     * @param forbid 是否包含禁用的角色信息（no/yes）
     * @return
     */
    public JsonResult getGroupRoles(String forbid){
        //获取所有的分组
        List list = roleDao.getRoleGroups();
        List rolesGroupResult = new ArrayList();
        for(int i=0;i<list.size();i++){
            JSONObject rolesGroup = new JSONObject();
            Object[] groupInfo = (Object[])list.get(i);
            List<Role> rolesOfGroup = roleDao.getRolesByGroup(groupInfo[0].toString());
            if(StringUtils.isNotBlank(forbid)&&forbid.equals("yes")){
                List<Role> rolesOfGroupObj = new ArrayList<Role>();
                //过滤掉禁用的角色
                for(Role role:rolesOfGroup){
                    if(role.getState()==1){
                        rolesOfGroupObj.add(role);
                    }
                }
                rolesOfGroup = rolesOfGroupObj;
            }
            if(rolesOfGroup.size()>0){
                rolesGroup.put("roleGroup",groupInfo[0]);
                rolesGroup.put("roleGroupName",groupInfo[1]);
                rolesGroup.put("items",rolesOfGroup);
                rolesGroupResult.add(rolesGroup);
            }
        }
        return JsonResult.success(rolesGroupResult);
    }

    /**
     * 通过id获取角色信息
     * @param id
     * @return
     */
    public Role getRoleById(String id){
        return roleDao.findOne(id);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    public Role addRole(Role role){
        role.setCreateTime(new Date());
        return roleDao.saveAndFlush(role);
    }

    /**
     * 获取角色组信息
     * @return
     */
    public JsonResult getRoleGroups(){
        List list = roleDao.getRoleGroups();
        List groupsList = new ArrayList();
        for(int i=0;i<list.size();i++){
            Object[] obj = (Object[])list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("groupId",obj[0]);
            jsonObject.put("groupName",obj[1]);
            groupsList.add(jsonObject);
        }
        return JsonResult.success(groupsList);
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    public JsonResult updateRole(JSONObject role){
        Integer res = roleDao.updateRole(role.getInteger("id"),
                role.getString("cn"),
                role.getString("en"),
                role.getInteger("state"),
                role.getString("description"),
                new Date(),
                role.getInteger("state").equals(0)?new Date():null,
                role.getString("roleGroup"),
                role.getString("roleGroupDesc")
                );
        return JsonResult.success(res);
    }

    /**
     *添加用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @param defaultRole 默认角色
     * @param roleGroup 角色组
     * @return
     */
    public Integer addRoleToUser(String userId,String roleId,Integer defaultRole,String roleGroup){
        return roleDao.addRoleToUser(userId,roleId,null,null);
    }

    /**
     * 通过用户id获取用户角色
     * @param userId
     * @return
     */
    public JSONObject getRolesOfUser(String userId){
        List rolesOfUser = roleDao.getRolesOfUser(userId);
        JSONObject jsonObject = new JSONObject();
        Object[] list = (Object[])rolesOfUser.get(0);
        jsonObject.put("userId",list[0]);
        jsonObject.put("rolesIds",list[1]);
        jsonObject.put("defaultRole",list[2]);
        jsonObject.put("groupRoles",list[3]);
        return jsonObject;
    }

    /**
     * 更新用户角色信息
     * @param userId 用户id
     * @param roleId 角色id
     * @param defaultRole 暂时不用，保留字段
     * @param roleGroup 暂时不用，保留字段
     * @return
     */
    @Transactional
    public Integer updateRoleOfUser(String userId,String roleId,Integer defaultRole,String roleGroup){
        return roleDao.updateRoleToUser(userId,roleId,0,roleGroup);
    }

}
