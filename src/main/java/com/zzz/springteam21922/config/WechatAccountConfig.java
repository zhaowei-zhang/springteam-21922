package com.zzz.springteam21922.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */

/**
 * 获取配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
}
