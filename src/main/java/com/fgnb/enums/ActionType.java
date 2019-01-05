package com.fgnb.enums;

/**
 * Created by jiangyitao.
 */
public enum ActionType {

    BASE_ACTION(1),
    CUSTOM_ACTION(2),
    TESTCASE_ACTION(3),
    TEST_PLAN_BEFORE_ACTION(4);

    private int type;

    ActionType(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
