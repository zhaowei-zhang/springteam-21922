package com.zzz.springteam21922.domain;

import lombok.Data;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */
@Data
public class WxUserModel {
    private String userid;
    private String deviceId;
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

}
