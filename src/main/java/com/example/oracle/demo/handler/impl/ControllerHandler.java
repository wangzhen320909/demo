package com.example.oracle.demo.handler.impl;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.model.ControllerInfo;

import java.io.File;

public class ControllerHandler extends BaseHandler<ControllerInfo> {

    public ControllerHandler(String ftlName, ControllerInfo info) {
        this.ftlName = ftlName;
        this.info = info;
        this.savePath = Configuration.getString("base.baseDir") + File.separator
                + Configuration.getString("controller.path") + File.separator + info.getClassName()
                + Constants.FILE_SUFFIX_JAVA;

    }

    @Override
    public void combileParams(ControllerInfo controllerInfo) {
        this.param.put("packageStr", controllerInfo.getPackageStr());
        this.param.put("voPackage", controllerInfo.getVoInfo().getPackageStr());
        this.param.put("voClassName", controllerInfo.getVoInfo().getClassName());

		this.param.put("dtoPackage", controllerInfo.getDtoInfo().getPackageStr());
		this.param.put("dtoClassName", controllerInfo.getDtoInfo().getClassName());

        String className = controllerInfo.getClassName();
        this.param.put("className",className);
        this.param.put("lowerClassName", className.substring(0,1).toLowerCase() + className.substring(1));
        this.param.put("servicePackage", controllerInfo.getServiceInfo().getPackageStr());
        String serviceName = controllerInfo.getServiceInfo().getClassName();
        this.param.put("serviceName", serviceName);
        this.param.put("lowerServiceName", serviceName.substring(0,1).toLowerCase() + serviceName.substring(1));
        this.param.put("packageStr", controllerInfo.getPackageStr());
        this.param.put("entityName", controllerInfo.getEntityName());
        this.param.put("requestMapping", controllerInfo.getRequestMapping());
        this.param.put("entityPackage", controllerInfo.getEntityPackage());
        this.param.put("entityDesc", controllerInfo.getEntityDesc());

    }
}
