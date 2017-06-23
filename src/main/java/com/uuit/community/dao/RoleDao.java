package com.uuit.community.dao;

import com.uuit.community.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbin on 2016/7/4.
 */
public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    /**
     * 获取所有的角色组信息
     * @return
     */
    @Query(value="SELECT roleGroup,roleGroupDesc FROM auth_role GROUP BY roleGroup",nativeQuery = true)
    List getRoleGroups();


    @Query("SELECT r FROM Role r WHERE roleGroup=:roleGroup")
    List<Role> getRolesByGroup(@Param("roleGroup") String roleGroup);

    /**
     * 更新角色
     *
     * @return
     */
    @Transactional
    @Modifying
    @Query(value="UPDATE auth_role SET en=:en,cn=:cn,state=:state,description=:description,updateTime=:updateTime,forbidTime=:forbidTime,roleGroup=:roleGroup,roleGroupDesc=:roleGroupDesc WHERE id=:id",nativeQuery = true)
    Integer updateRole(@Param("id") Integer id,
                       @Param("cn") String cn,
                       @Param("en") String en,
                       @Param("state") Integer state,
                       @Param("description") String description,
                       @Param("updateTime") Date updateTime,
                       @Param("forbidTime") Date forbidTime,
                       @Param("roleGroup") String roleGroup,
                       @Param("roleGroupDesc") String roleGroupDesc);

    /**
     * 添加用户角色
     * @param userId
     * @param roleId
     * @param defaultRole
     * @param roleGroup
     * @return
     */
    @Query(value="INSERT INTO auth_user_role(user_id,role_id,default_role,role_group) values (:id,:userId,:roleId,:defaultRole,:roleGroup)",nativeQuery = true)
    Integer addRoleToUser(@Param("userId")String userId,@Param("roleId")String roleId,@Param("defaultRole")Integer defaultRole,@Param("roleGroup")String roleGroup);

    /**
     * 更新用户角色
     * @param userId
     * @param roleId
     * @param defaultRole
     * @param roleGroup
     * @return
     */
    @Modifying
    @Query(value="UPDATE auth_user_role SET user_id=:userId,role_id=:roleId,default_role=:defaultRole,role_group=:roleGroup WHERE user_id=:userId",nativeQuery = true)
    Integer updateRoleToUser(@Param("userId") String userId, @Param("roleId")String roleId, @Param("defaultRole")Integer defaultRole, @Param("roleGroup")String roleGroup);

    /**
     * 通过id查询用户角色
     * @param userId
     * @return
     */
    @Query(value="SELECT user_id,role_id,default_role,role_group FROM auth_user_role WHERE user_id=:userId",nativeQuery = true)
    List getRolesOfUser(@Param("userId") String userId);
}
