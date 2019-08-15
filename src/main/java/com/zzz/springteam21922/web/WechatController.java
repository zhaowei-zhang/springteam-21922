package com.zzz.springteam21922.web;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */
import com.zzz.springteam21922.dto.Qm;
import com.zzz.springteam21922.util.MyWeCHatUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 微信授权
 */
@RestController
@RequestMapping("/public/wechat")
public class WechatController {

    private Logger logger = Logger.getLogger(getClass());


    @Autowired
    private WxMpService wxMpService;


    @GetMapping("/getqm")
    public ResponseEntity<Qm> getQm(String url) throws IOException {
        Qm qm = MyWeCHatUtil.getQm(url);
        return ResponseEntity.ok().body(qm);
    }


    /**
     * @Description: 微信授权
     * @Param: [returnUrl]
     * @returns: java.lang.String
     * @Author: zhuenbang
     * @Date: 2018/8/11 15:08
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        String url = "http://xx.com/api/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        logger.infov("【微信网页授权】获取code,redirectUrl={}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * @Description: 微信授权回调用户信息
     * @Param: [code, returnUrl]
     * @returns: java.lang.String
     * @Author: zhuenbang
     * @Date: 2018/8/11 15:08
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        WxMpUser wxMpUser;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            logger.debugv("获取用户信息：{}", wxMpUser);
            return wxMpUser.getSexDesc();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }


        return "授权失败";
    }
}
