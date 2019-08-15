package com.zzz.springteam21922.web;

import com.zzz.springteam21922.dto.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/14
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping(value = "/createNewUser")
    public String createNewUser(@RequestBody User user) {
        return "登陆成功";

    }


}
