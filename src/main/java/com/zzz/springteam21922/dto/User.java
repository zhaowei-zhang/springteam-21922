package com.zzz.springteam21922.dto;

import com.zzz.springteam21922.domain.Role;
import com.zzz.springteam21922.domain.UserTable;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/13
 */
@Data
public class User {
    private UserTable userbody;
    private List<Role> roles;
    private String checkCode;
}