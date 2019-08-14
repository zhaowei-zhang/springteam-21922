package com.zzz.springteam21922.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/13
 */
@Data
public class Role implements GrantedAuthority {

    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
