package com.zzz.springteam21922.dto;

import com.zzz.springteam21922.dto.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/14
 */
public class JwtUser extends org.springframework.security.core.userdetails.User {




    public JwtUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserbody().getUsername(), user.getUserbody().getPassword(), authorities);


    }

}
