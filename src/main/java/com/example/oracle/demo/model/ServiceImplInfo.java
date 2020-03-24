package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class ServiceImplInfo {

    private String className;

    private String packageStr;

    private String serviceType;

    private String voType;

    private String dtoType;

    private String entityDesc;

    private String lowerEntityName;

    private String entityName;

    private String entityPackage;

    private String daoType;

    private VoInfo voInfo;

	private DtoInfo dtoInfo;

    private ServiceInfo serviceInfo;

}
