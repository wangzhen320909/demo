package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class DtoInfo {

    /**
     * 包路径
     */
    private String packageStr;

    /**
     * 类名
     */
    private String className;

    /**
     * 实体信息
     */
    private EntityInfo entityInfo;


}
