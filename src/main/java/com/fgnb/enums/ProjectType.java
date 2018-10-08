package com.fgnb.enums;


/**
 * Created by jiangyitao.
 */
public enum ProjectType {

    ANDROID("Android",1),
    IOS("iOS",2);

    private int type;
    private String name;

    ProjectType(String name, int i) {
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
        for(ProjectType projectType : ProjectType.values()){
            if(projectType.getType() == type){
                return projectType.getName();
            }
        }
        return null;
    }
}
