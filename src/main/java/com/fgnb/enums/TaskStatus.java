package com.fgnb.enums;

/**
 * Created by jiangyitao.
 */
public enum TaskStatus {

    RUNNING(0),
    FINISHED(1),
    ERROR(2);

    private int status;

    TaskStatus(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
