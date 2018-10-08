package com.fgnb.enums;

/**
 * Created by jiangyitao.
 */
public enum TestPlanBeforeType {

    BEFORE_SUITE(1),BEFORE_METHOD(2);

    private int type;

    TestPlanBeforeType(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
