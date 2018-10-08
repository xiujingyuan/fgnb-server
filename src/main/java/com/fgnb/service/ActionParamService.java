package com.fgnb.service;

import com.fgnb.domain.ActionParam;
import com.fgnb.mapper.ActionParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class ActionParamService {

    @Autowired
    private ActionParamMapper actionParamMapper;

    public List<ActionParam> findActionParamsByActionId(Integer actionId) {
        return actionParamMapper.findActionParamsByActionId(actionId);
    }
}
