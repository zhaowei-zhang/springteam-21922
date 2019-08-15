package com.zzz.springteam21922.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ConnHmapPost {
    private static final Logger log = LoggerFactory.getLogger(ConnHmapPost.class);

    public static String ParamFromGet(String get_url) {
        URL url;
        String return_str = "";
        try {
            url = new URL(get_url);
            try {
                URLConnection connection = url.openConnection();
                BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sb = null;
                String line;
                while ((line = buff.readLine()) != null) {
                    sb.append(line);
                }
                return_str = sb.toString();
                System.out.println(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return return_str;
    }

    /**
     * post方式//连接主逻辑
     *
     * @param post_url
     * @return
     * @throws Exception
     */
    public static String ParamFromPost(String post_url, String param) throws IOException {
        String return_str = "";
        try {
            // Post请求的url，与get不同的是不需要带参数
            URL postUrl = new URL(post_url);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(param);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // 读取数据
                sb.append(line);
            }
            writer.close();
            reader.close();
            connection.disconnect();
            return_str = sb.toString();
        } catch (Exception e) {
            return_str = "{\"status\":\"error\",\"message\":\"" + "连接失败:" + e.getMessage() + "\"}";
            log.error("连接失败:" + e.getMessage());
        }
        log.info("return_str = " + return_str);
        return return_str;
    }

    public static String ContentFromPost(String post_url, String content) throws IOException {
        String return_str = "";
        try {
            // Post请求的url，与get不同的是不需要带参数
            URL postUrl = new URL(post_url);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            // 设置超时
            connection.setConnectTimeout(30000);// 连接10秒
            connection.setReadTimeout(30000);// 读取10秒
            connection.setRequestProperty("encoding", "UTF-8");
            connection.setRequestProperty("Accept", "application/json");// 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json charset=UTF-8");
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            // writer.write(param);
            writer.write(content);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // 读取数据
                sb.append(line);
            }
            writer.close();
            reader.close();
            connection.disconnect();
            return_str = sb.toString();
        } catch (Exception e) {
            return_str = "{\"status\":\"error\",\"message\":\"" + "连接失败:" + e.getMessage() + "\"}";
            log.error("连接失败:" + e.getMessage());
        }
        log.info("return_str = " + return_str);
        return return_str;
    }

    public static String get(String url) {
//		HttpGet httpGet = new HttpGet(url);
//		HttpClient client = new DefaultHttpClient();
//		HttpResponse response = null;
//		String result = "";
//		try {
//			log.error("get请求url:" + url);
//			response = client.execute(httpGet);
//			result = EntityUtils.toString(response.getEntity(), "UTF-8");
//			log.info("get请求url:" + url);
//		} catch (Exception e) {
//			result = "{\"status\":\"error\",\"message\":\"" + "连接失败:" + e.getMessage() + "\"}";
//			log.error("连接失败:" + e.getMessage());
//		} finally {
//			client.getConnectionManager().shutdown();
//		}
        CloseableHttpClient httpClient = null;
        String result = "";
        CloseableHttpResponse response = null;
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            result = "{\"status\":\"error\",\"message\":\"" + "连接失败:" + e.getMessage() + "\"}";
            log.error("连接失败:" + e.getMessage());
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String post(String url, String content) {
        HttpPost httpPost = new HttpPost(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String result = "";
        try {
            StringEntity entity = new StringEntity(content, "UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            log.error("post请求url:" + url);
            response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            result = "{\"status\":\"error\",\"message\":\"" + "连接失败:" + e.getMessage() + "\"}";
            log.error("连接失败:" + e.getMessage());
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }

//	
//	public static void main(String[] args) throws Exception {  
//        String strUrl="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww8fc9a3e127ab4b64&corpsecret=VMjqQ473RikjF97vhDKHAGhK5CubHE9mQNTVpa1AuNM";
//        String respon=get(strUrl);
//        System.out.println(respon);
//    }
}