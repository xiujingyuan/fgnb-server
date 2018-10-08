package com.fgnb.service;

import com.fgnb.domain.User;
import com.fgnb.dto.UserDTO;
import com.fgnb.exception.BusinessException;
import com.fgnb.mapper.UserMapper;
import com.fgnb.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username,String password){
        User user = userMapper.findByUsernameAndPassword(username, password);
        if(user == null){
            throw new BusinessException("账号或密码错误");
        }
        user.setToken(TokenUtil.create(user.getUserId() + ""));
        return user;
    }

    public List<UserDTO> findAll() {
        return userMapper.findAll();
    }
}
