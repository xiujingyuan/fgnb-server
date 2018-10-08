package com.fgnb.controller;

import com.fgnb.service.UserService;
import com.fgnb.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangyitao.
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response login(String username, String password){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return Response.fail("账号或密码不能为空");
        }
        return Response.success("登录成功",userService.login(username,password));
    }

    @GetMapping("/list")
    public Response list(){
        return Response.success(userService.findAll());
    }
}
