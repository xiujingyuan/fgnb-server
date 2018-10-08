package com.fgnb.enums;

/**
 * Created by jiangyitao.
 */
public enum TestTaskRunMode {

    //1.兼容  2.高效
    COMPATIBLE(1),EFFICIENCY(2);

    private int type;

    TestTaskRunMode(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
