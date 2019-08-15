package com.zzz.springteam21922.web;

import com.zzz.springteam21922.domain.UserTable;
import com.zzz.springteam21922.dto.User;
import com.zzz.springteam21922.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/14
 */

@RestController
@RequestMapping("/public/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login/{code}")
    public ResponseEntity<UserTable>  login(@RequestBody User user, @PathVariable(value = "code")String code) {
        return userService.login(user,code);
    }


}
