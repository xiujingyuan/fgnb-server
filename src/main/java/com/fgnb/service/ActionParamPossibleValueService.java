package com.fgnb.service;

import com.fgnb.domain.ActionParamPossibleValue;
import com.fgnb.mapper.ActionParamPossibleValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Service
public class ActionParamPossibleValueService {

    @Autowired
    private ActionParamPossibleValueMapper actionParamPossibleValueMapper;

    public List<ActionParamPossibleValue> findActionParamPossibleValuesByActionParamId(Integer actionParamId) {
        return actionParamPossibleValueMapper.findByActionParamId(actionParamId);
    }
}
