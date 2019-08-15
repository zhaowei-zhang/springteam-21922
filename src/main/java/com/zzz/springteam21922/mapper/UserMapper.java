package com.zzz.springteam21922.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zzz.springteam21922.domain.UserTable;
import com.zzz.springteam21922.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/13
 */
@Mapper
public interface UserMapper extends BaseMapper<UserTable> {
//    @Select( "select id , username , password , wxuserid from user where username = #{username}" )
//    User loadUserByUsername(@Param("username") String username);



}