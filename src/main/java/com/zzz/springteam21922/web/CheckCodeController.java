package com.zzz.springteam21922.web;

import com.zzz.springteam21922.dto.CheckModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @description:
 * @version: 1.0
 * @author: zhaowei.zhang01@hand-china.com
 * @date: 2019/8/14
 */

@RestController
@RequestMapping("/public/check")
public class CheckCodeController {

    @GetMapping("/getCode/{id}")
    public ResponseEntity<CheckModel> getCode(@PathVariable(value = "id") String id) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(random.nextInt(str.length()));
            sb.append(ch);
        }
        CheckModel checkModel=new CheckModel();
        checkModel.setCheck(sb.toString());
        return ResponseEntity
                .ok().body(checkModel);
    }

}
