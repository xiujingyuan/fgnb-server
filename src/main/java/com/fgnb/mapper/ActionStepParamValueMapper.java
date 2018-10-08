package com.fgnb.mapper;

import com.fgnb.domain.ActionStepParamValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ActionStepParamValueMapper {
    int addActionStepParamValues(List<ActionStepParamValue> actionStepParamValues);
}
