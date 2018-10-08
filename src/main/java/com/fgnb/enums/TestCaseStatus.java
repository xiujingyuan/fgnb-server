package com.fgnb.enums;

/**
 * Created by jiangyitao.
 */
public enum TestCaseStatus {
    FAIL(0),
    SUCCESS(1),
    SKIP(2);

    private int status;

    TestCaseStatus(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
