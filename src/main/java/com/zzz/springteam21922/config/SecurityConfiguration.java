package com.zzz.springteam21922.config;

import com.zzz.springteam21922.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.DigestUtils;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/13
 */

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
//        //校验用户
//        auth.userDetailsService(myUserDetailsService).passwordEncoder(new PasswordEncoder() {
//            //对密码进行加密
//            @Override
//            public String encode(CharSequence charSequence) {
//                System.out.println("1:encode.charSequence:"+charSequence.toString());
//                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
//            }
//            //对密码进行判断匹配
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                System.out.println("2:matches.charSequence:"+charSequence);
//                System.out.println("3:s:"+s);
//                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
//                boolean res = s.equals( encode );
//                return res;
//            }
//        } );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .and()
                .formLogin()
                .permitAll();

    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("healthy");
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        // 使用redis存储token信息
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        return redisTokenStore;
    }

    /**
     * *需要配置这个支持password模式 support password grant type
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}