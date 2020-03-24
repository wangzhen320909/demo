package com.example.oracle.demo.handler.impl;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.model.ServiceInfo;

import java.io.File;

public class ServiceHandler extends BaseHandler<ServiceInfo> {

    public ServiceHandler(String ftlName, ServiceInfo info) {
        this.info = info;
        this.ftlName = ftlName;
        this.savePath = Configuration.getString("base.baseDir") + File.separator
                + Configuration.getString("service.path") + File.separator + info.getClassName()
                + Constants.FILE_SUFFIX_JAVA;
    }

    @Override
    public void combileParams(ServiceInfo info) {
        this.param.put("packageStr", info.getPackageStr());
        this.param.put("entityDesc", info.getEntityDesc());
        this.param.put("className", info.getClassName());
        this.param.put("entityName", info.getEntityName());
        this.param.put("entityPackage", info.getEntityPackage());
        this.param.put("voName", info.getVoInfo().getClassName());
        this.param.put("voPackage", info.getVoInfo().getPackageStr());

    }

}
