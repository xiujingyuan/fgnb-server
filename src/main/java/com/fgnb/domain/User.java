package com.fgnb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * Created by jiangyitao.
 */
@Data
public class User {

    private Integer userId;

    private String username;
    @JsonIgnore
    private String password;

    private String nickname;
    @JsonIgnore
    private Date createTime;

    private String token;
}
