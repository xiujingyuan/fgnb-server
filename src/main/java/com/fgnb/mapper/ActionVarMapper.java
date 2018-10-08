package com.fgnb.mapper;

import com.fgnb.domain.ActionVar;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ActionVarMapper {

    int addActionVars(List<ActionVar> actionVars);

    @Delete("delete from action_var where actionId = #{actionId}")
    int deleteActionVarsByActionId(Integer actionId);
}
