package com.zzz.springteam21922.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */

@Data
@TableName(value = "user")
public class UserTable {

    @TableField(value = "id")
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "wxuserid")
    private String wxuserid;
}


