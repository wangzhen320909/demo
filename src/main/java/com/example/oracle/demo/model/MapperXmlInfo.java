package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class MapperXmlInfo {

    private String nameSpace;

    private String className;

    private ServiceInfo serviceInfo;

    private EntityInfo entityInfo;

    private VoInfo voInfo;

}
