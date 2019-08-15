package com.zzz.springteam21922.util;

import com.alibaba.fastjson.JSONObject;
import com.zzz.springteam21922.domain.WxModel;
import com.zzz.springteam21922.domain.WxUserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/15
 */
public class MyWeCHatUtil {

    private static final String agentid="1000002";//agentid:1000015
    private static final String WxUserUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";//WxUserUrl
    private static final String corpsecret="j6n319KDSd4xXND6fHOjpvafv3E-qeiYrX359OH3T8c";//corpsecret：ZY7-_CrxrzJg-3UC8gaKC_yz8sWppLTvQZeaZWSq8d4
    private static final String corpid="ww98e801cc596c14d5";//corpid：ww94243a4a3ae59529
    private static final String WxTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";//WxTokenUrl


    public static List<WxModel> wx_token = new ArrayList<WxModel>();
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
    private static final Logger log = LoggerFactory.getLogger(MyWeCHatUtil.class);



    /**
     *获取企业微信token
     *
     */
    private static String Gettoken() {
        //获取前先清理历史token，只需要存在一个即可
        wx_token.clear();
        String url=null;
        String access_token=null;
        Integer expires_in=null;
//        String corpsecret="ZY7-_CrxrzJg-3UC8gaKC_yz8sWppLTvQZeaZWSq8d4";//corpsecret：j6n319KDSd4xXND6fHOjpvafv3E-qeiYrX359OH3T8c
//        String corpid="ww94243a4a3ae59529";//corpid：ww98e801cc596c14d5
        url = WxTokenUrl + "?corpid=" + corpid + "&corpsecret=" + corpsecret;
        String response = ConnHmapPost.get(url);
        log.info("获取结果:" +response);
        JSONObject tokenObject = JSONObject.parseObject(response);
        try{
            access_token = tokenObject.getString("access_token");
            expires_in = Integer.valueOf(tokenObject.getString("expires_in"));
            WxModel wxModel=new WxModel();
            // 记录请求时间
            String token_time_str = format.format(System.currentTimeMillis());
            wxModel.setToken(access_token);
            wxModel.setExpires_in(expires_in);
            wxModel.setToken_begin_str(token_time_str);
            wx_token.add(wxModel);
        }catch(Exception e){
            access_token="";
        }
        return access_token;
    }




    private static String checkToken(){
        String return_token=null;
        String access_token=null;
        Integer expires_in=null;
        String token_time_str=null;
        Date d = new Date();
        Date lttsdate = null;
        if(wx_token.size()>0){
            log.info("存在历史token，缓存取值");
            access_token=wx_token.get(0).getToken();
            expires_in=wx_token.get(0).getExpires_in();
            log.info("历史token有效期:"+expires_in);
            token_time_str=wx_token.get(0).getToken_begin_str();
            try {
                lttsdate = format.parse(token_time_str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 得到相差秒数
            long lt = (d.getTime() - lttsdate.getTime()) / 1000;
            log.info("相差秒数:"+lt);
            if (lt >= expires_in) {
                return_token=Gettoken();
            }else{
                return_token=access_token;
            }
        }else{
            log.info("不存在历史token，重新请求token");
            return_token=Gettoken();
        }
        return return_token;
    }


    private static JSONObject WxPostCode(String code,String access_token){
        log.info("------------------------------------请求用户信息---------------------------------------");
        String url = WxUserUrl + "?access_token=" + access_token + "&agentid=" + agentid+ "&code=" + code+"&connect_redirect=1";
        String response = ConnHmapPost.get(url);
        JSONObject UserObject = JSONObject.parseObject(response);
        log.info("UserObject: ------======"+UserObject.toString());
        log.info("------------------------------------请求用户信息结束---------------------------------------");
        return UserObject;
    }

    public static WxUserModel getWxUser(String code){
        log.info("------------------------------------开始获取企业微信用户信息---------------------------------------");
        WxUserModel wxUserModel=null;
        String access_token=null;

        access_token=checkToken();

        JSONObject UserObject=WxPostCode(code,access_token);

        if("40014".equalsIgnoreCase(UserObject.getString("errcode")) && "invalid access_token".equals(UserObject.getString("errmsg"))){
            access_token=Gettoken();
            UserObject=WxPostCode(code,access_token);
            wxUserModel=getUD(UserObject);
        }else{
            wxUserModel=getUD(UserObject);
        }
        log.info("------------------------------------结束获取企业微信用户信息---------------------------------------");
        return wxUserModel;
    }
    private static WxUserModel getUD(JSONObject jobj){
        WxUserModel wxUserModel=null;
        log.info("------------------------------------开始提取用户信息---------------------------------------");
        log.info(jobj.toString());
        if("0".equalsIgnoreCase(jobj.getString("errcode")) && "ok".equals(jobj.getString("errmsg"))){
            String UserId=jobj.getString("UserId");
            String DeviceId=jobj.getString("DeviceId");
            wxUserModel=new WxUserModel();
            wxUserModel.setDeviceId(DeviceId);
            wxUserModel.setUserid(UserId);
        }
        return wxUserModel;
    }







    public static void main(String[] args) {
        log.info("------------------------------------测试信息---------------------------------------");
        WxUserModel user = getWxUser("VevMz0ad5bcBlheQNRl26xhHIlo0UKXcXgbPew4B16c");
        System.out.println();
    }
}
