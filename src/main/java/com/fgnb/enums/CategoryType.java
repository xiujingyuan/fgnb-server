package com.fgnb.enums;

/**
 * Created by jiangyitao.
 */
public enum CategoryType {

    /* 0.全局变量 */
    GLOBAL_VAR(0),PAGE(1),TESTCASE(2);

    private int type;

    CategoryType(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
