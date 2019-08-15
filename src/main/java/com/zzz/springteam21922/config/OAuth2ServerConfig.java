package com.zzz.springteam21922.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @description:
 * @version: 1.0
 * @author: xiantao.han@hand-china.com
 * @Date: 2019/8/14
 */


@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    @Autowired
    private ClientDetailsService clientDetails;


    // 配置令牌端点(Token Endpoint)的安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                // code授权添加
                .realm("oauth2-resource")//oauth2-resources,oauth2-dev
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 接口/oauth/check_token允许检查令牌
                .checkTokenAccess("isAuthenticated()")
                // 使/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients()
                // 密码编码器
                .passwordEncoder(passwordEncoder);
    }

    // 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                // 认证管理器
                .authenticationManager(authenticationManager)
                // 允许 GET、POST 请求获取 token，即访问端点：oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(userDetailsService)
                // 指定token存储位置
                .tokenStore(tokenStore)
                // 配置JwtAccessToken转换器
                .accessTokenConverter(accessTokenConverter)
                // 客户端详细信息服务的基本实现 这里使用JdbcClientDetailsService
                .setClientDetailsService(clientDetails);
    }

    // 配置客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 内存模式
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        clients.inMemory().withClient("springteam-21922").secret(bCryptPasswordEncoder.encode("springteam-21922Secret"))
         .redirectUris("http://baidu.com")// code授权添加
         .authorizedGrantTypes("authorization_code", "client_credentials", "password", "refresh_token")
         // scopes的值就是all（全部权限），read，write等权限。就是第三方访问资源的一个权限，访问范围
         .scopes("all")
         // 这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
         .resourceIds("oauth2-resource")
         // 设置accessTokenValiditySeconds属性来设置Access Token的存活时间。
         .accessTokenValiditySeconds(1200)
         // 设置refreshTokenValiditySeconds属性来设置refresh Token的存活时间。
         .refreshTokenValiditySeconds(50000);


        // 数据库模式
//        clients.withClientDetails(clientDetails); // 表中存储的secret值是加密后的值，并非明文
    }


    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("springteam-21922Secret");
        System.out.println(encode);
    }
}
