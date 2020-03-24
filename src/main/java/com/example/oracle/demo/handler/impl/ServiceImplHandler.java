package com.example.oracle.demo.handler.impl;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.model.ServiceImplInfo;

import java.io.File;

public class ServiceImplHandler extends BaseHandler<ServiceImplInfo> {

    public ServiceImplHandler(String ftlName, ServiceImplInfo info) {
        this.ftlName = ftlName;
        this.info = info;
        this.savePath = Configuration.getString("base.baseDir") + File.separator
                + Configuration.getString("serviceImpl.path") + File.separator + info.getClassName()
                + Constants.FILE_SUFFIX_JAVA;
    }

    @Override
    public void combileParams(ServiceImplInfo info) {
        this.param.put("packageStr", info.getPackageStr());
        this.param.put("serviceType", info.getServiceType());
        String voType = info.getVoType();
        String utilProject = voType.substring(0,info.getPackageStr().lastIndexOf(".service.impl"));
        utilProject = utilProject.substring(utilProject.lastIndexOf(".")+1);

        this.param.put("voType", voType);
        this.param.put("utilProject", utilProject);
        this.param.put("daoType", info.getDaoType());
        this.param.put("entityDesc", info.getEntityDesc());
        this.param.put("lowerEntityName", info.getLowerEntityName());
        this.param.put("entityName", info.getEntityName());
        this.param.put("entityPackage", info.getEntityPackage());
    }
}
