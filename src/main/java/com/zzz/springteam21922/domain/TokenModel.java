package com.zzz.springteam21922.domain;

import lombok.Data;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */
@Data
public class TokenModel {

    private Integer expires_in;
    private String token;
    private String token_begin_str;
}
