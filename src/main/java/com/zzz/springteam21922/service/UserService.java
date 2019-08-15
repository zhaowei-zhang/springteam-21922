package com.zzz.springteam21922.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzz.springteam21922.domain.Role;
import com.zzz.springteam21922.domain.UserTable;
import com.zzz.springteam21922.dto.User;
import com.zzz.springteam21922.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserTable> {


    public User getUserByUserName(String userName){
        UserTable selectWhere = new UserTable();
        selectWhere.setUsername(userName);
        UserTable selectUser = baseMapper.selectOne(selectWhere);
        User user = new User();
        user.setUserbody(selectUser);
        user.setRoles(new ArrayList<Role>());
        return user;
    }
}
