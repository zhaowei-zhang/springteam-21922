package com.zzz.springteam21922.dto;

import lombok.Data;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/16
 */
@Data
public class Qm {
    private String jsapi_ticket;
    private String noncestr;
    private long data;
    private String url;
    private String qm;
}
