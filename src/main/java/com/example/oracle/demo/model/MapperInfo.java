package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class MapperInfo {

	/**
	 * XXXMapper.xml
	 */
	private String fileName;

	private String namespace;

	private DaoInfo daoInfo;

	private EntityInfo entityInfo;


}
