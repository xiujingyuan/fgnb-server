package com.fgnb.enums;


/**
 * Created by jiangyitao.
 */
public enum DeviceType {

    ANDROID("Android",1),
    IOS("iOS",2);

    private int type;
    private String name;

    DeviceType(String name, int i) {
        this.name = name;
        this.type = i;
    }

    public int getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public static String getName(int type){
        for(DeviceType projectType : DeviceType.values()){
            if(projectType.getType() == type){
                return projectType.getName();
            }
        }
        return null;
    }
}
