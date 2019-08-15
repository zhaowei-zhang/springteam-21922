package com.zzz.springteam21922.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzz.springteam21922.domain.Role;
import com.zzz.springteam21922.domain.UserTable;
import com.zzz.springteam21922.dto.WxUserModel;
import com.zzz.springteam21922.dto.User;
import com.zzz.springteam21922.mapper.UserMapper;
import com.zzz.springteam21922.util.MyWeCHatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserTable> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUserName(String userName){
        UserTable selectWhere = new UserTable();
        selectWhere.setUsername(userName);
        UserTable selectUser = baseMapper.selectOne(selectWhere);
        User user = new User();
        user.setUserbody(selectUser);
        user.setRoles(new ArrayList<Role>());
        return user;
    }


    public ResponseEntity<UserTable> login(User user, String code) {
        user.getCheckCode();


        UserTable userTable = user.getUserbody();
        WxUserModel wxUser = MyWeCHatUtil.getWxUser(code);
        if(wxUser==null){
            wxUser = new WxUserModel();
            wxUser.setUserid("这是个假的");
        }
        UserTable wx = new UserTable();
        wx.setWxuserid(wxUser.getUserid());
        UserTable selectWxUser = baseMapper.selectOne(wx);
        if(selectWxUser!=null){
            return ResponseEntity
                    .ok().body(selectWxUser);
        }
        else{
            userTable.setPassword(passwordEncoder.encode(userTable.getPassword()));
            baseMapper.insert(userTable);
            return ResponseEntity
                    .ok().body(userTable);
        }
    }
}
