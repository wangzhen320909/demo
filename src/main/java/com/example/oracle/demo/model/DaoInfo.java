package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class DaoInfo {

    private String packageStr;

    private String className;

    private EntityInfo entityInfo;

    private VoInfo voInfo;


}
