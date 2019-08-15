package com.zzz.springteam21922.service;

import com.zzz.springteam21922.dto.JwtUser;
import com.zzz.springteam21922.domain.Role;
import com.zzz.springteam21922.dto.User;
import com.zzz.springteam21922.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/13
 */
@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查数据库
        User user = userService.getUserByUserName(username);
        List<GrantedAuthority> lg = new ArrayList();
        List<Role> roles = user.getRoles();
//        Role role1 = new Role();
//        role1.setId(1L);
//        role1.setName("admin");
//        roles.add(role1);
        for (Role role : roles) {
            lg.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new JwtUser(user, lg);
    }
}