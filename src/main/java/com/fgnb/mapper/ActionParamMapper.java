package com.fgnb.mapper;

import com.fgnb.domain.ActionParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ActionParamMapper {

    int addActionParams(List<ActionParam> actionParams);

    @Select("select * from action_param where actionId = #{actionId}")
    List<ActionParam> findActionParamsByActionId(Integer actionId);
}
