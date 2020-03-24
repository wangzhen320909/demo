package com.example.oracle.demo.handler.impl;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.model.DaoInfo;

import java.io.File;

public class DaoHandler extends BaseHandler<DaoInfo> {

    public DaoHandler(String ftlName, DaoInfo info) {
        this.ftlName = ftlName;
        this.info = info;
        this.savePath = Configuration.getString("base.baseDir") + File.separator
                + Configuration.getString("dao.path") + File.separator + info.getClassName()
                + Constants.FILE_SUFFIX_JAVA;

    }

    @Override
    public void combileParams(DaoInfo daoInfo) {
        this.param.put("packageStr", daoInfo.getPackageStr());
        this.param.put("className", daoInfo.getClassName());
        this.param.put("entityClass", daoInfo.getEntityInfo().getClassName());
        this.param.put("entityPackage", daoInfo.getEntityInfo().getEntityPackage());
        this.param.put("voClass", daoInfo.getVoInfo().getClassName());
        this.param.put("coPackage", daoInfo.getVoInfo().getPackageStr());
        this.param.put("entityDesc", daoInfo.getEntityInfo().getEntityDesc());
    }
}
