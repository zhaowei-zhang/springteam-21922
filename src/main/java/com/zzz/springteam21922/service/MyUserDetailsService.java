package com.zzz.springteam21922.service;

import com.zzz.springteam21922.domain.JwtUser;
import com.zzz.springteam21922.domain.Role;
import com.zzz.springteam21922.domain.User;
import com.zzz.springteam21922.mapper.RoleMapper;
import com.zzz.springteam21922.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        //查数据库
        User user = userMapper.loadUserByUsername(username);
        if (null != user) {
            List<Role> roles = roleMapper.getRolesByUserId( user.getId() );
            user.setRoles( roles );
        }
        List<GrantedAuthority> lg = new ArrayList();
        List<Role> roles = user.getRoles();
        for(Role role:roles){
            lg.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new JwtUser(user, lg);
//        log.debug("当前登陆用户名为:{}", username);
//        String password = passwordEncoder.encode("123456");
//        log.debug("当前登陆用户名密码为:{}", password);
//        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));

    }
}