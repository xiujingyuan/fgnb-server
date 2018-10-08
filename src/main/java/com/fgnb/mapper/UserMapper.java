package com.fgnb.mapper;

import com.fgnb.domain.User;
import com.fgnb.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface UserMapper {
    //根据账号密码查询用户
    @Select("select * from user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password")String password);

    @Select("select userId,nickname from user order by createTime desc")
    List<UserDTO> findAll();
}
