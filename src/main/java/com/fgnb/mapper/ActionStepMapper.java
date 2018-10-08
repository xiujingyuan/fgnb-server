package com.fgnb.mapper;

import com.fgnb.domain.ActionStep;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Mapper
public interface ActionStepMapper {

    int addActionSteps(List<ActionStep> actionSteps);

    @Delete("delete from action_step where actionId = #{actionId}")
    int deleteActionStepsByActionId(Integer actionId);
}
