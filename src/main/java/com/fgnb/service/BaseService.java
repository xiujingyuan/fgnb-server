package com.fgnb.service;

import com.fgnb.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiangyitao.
 */
@Slf4j
public class BaseService {
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 从请求头获取token，解析出用户id
     * @return
     * @throws Exception
     */
    protected Integer getUid(){
        try {
            String token = httpServletRequest.getHeader("token");
            return Integer.parseInt(TokenUtil.parse(token));
        }catch (Exception e){
            log.error("解析token出错",e);
            return null;
        }
    }

    protected Integer getProjectId(){
        String projectId = httpServletRequest.getHeader("projectId");
        if(StringUtils.isEmpty(projectId)){
            return null;
        }
        return Integer.valueOf(projectId);
    }
}
