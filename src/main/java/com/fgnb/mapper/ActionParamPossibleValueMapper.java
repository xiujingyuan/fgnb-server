package com.fgnb.mapper;

import com.fgnb.domain.ActionParamPossibleValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ActionParamPossibleValueMapper {
    @Select("select * from action_param_possible_value where actionParamId = #{actionParamId}")
    List<ActionParamPossibleValue> findByActionParamId(Integer actionParamId);
}
