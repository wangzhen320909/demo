package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class ControllerInfo {

	private String packageStr;
	private VoInfo voInfo;
	private DtoInfo dtoInfo;
	private String className;
	private ServiceInfo serviceInfo;
	private String entityName;
	private String requestMapping;
	private String entityPackage;
	private String entityDesc;


}
