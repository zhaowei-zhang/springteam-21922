package com.zzz.springteam21922.mapper;


import com.zzz.springteam21922.domain.RolePermisson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/13
 */
@Mapper
public interface PermissionMapper {

    @Select("SELECT A.NAME AS roleName,C.url FROM role AS A LEFT JOIN role_permission B ON A.id=B.role_id LEFT JOIN permission AS C ON B.permission_id=C.id")
    List<RolePermisson> getRolePermissions();
}