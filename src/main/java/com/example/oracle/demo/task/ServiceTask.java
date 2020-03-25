package com.example.oracle.demo.task;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.ServiceHandler;
import com.example.oracle.demo.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 央联支付有限公司
 * @since 2018-04-23
 * @version V1.0
 * @author  wzg
 * @desc
 **/
@Slf4j
public class ServiceTask extends AbstractApplicationTask {

    private static String SERVICE_FTL = "templates/Service.ftl";

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成service...");

        List<ServiceInfo> serviceInfos = (List<ServiceInfo>) context.getAttribute("serviceInfos");

        BaseHandler<ServiceInfo> baseHandler;
        for (ServiceInfo serviceInfo : serviceInfos) {
            baseHandler = new ServiceHandler(SERVICE_FTL, serviceInfo);
            baseHandler.execute(context);
        }

        log.info("结束生成service...");
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doAfter(ApplicationContext context) throws Exception {
        super.doAfter(context);

        List<ServiceInfo> serviceInfos = (List<ServiceInfo>) context.getAttribute("serviceInfos");
        List<EntityInfo> entityInfos = (List<EntityInfo>) context.getAttribute("entityInfos");
        List<DaoInfo> daoInfos = (List<DaoInfo>) context.getAttribute("daoList");
        List<VoInfo> voInfos = (List<VoInfo>) context.getAttribute("voList");
        List<DtoInfo> dtoInfos = (List<DtoInfo>) context.getAttribute("dtoList");

        List<ServiceImplInfo> serviceImplInfos = new ArrayList<>();
        ServiceImplInfo serviceImplInfo;
        for (int i = 0; i < serviceInfos.size(); i++) {
            ServiceInfo serviceInfo = serviceInfos.get(i);
            EntityInfo entityInfo = entityInfos.get(i);
            serviceImplInfo = new ServiceImplInfo();
            serviceImplInfo.setClassName(entityInfo.getEntityName() + Constants.SERVICE_IMPL_SUFFIX);
            serviceImplInfo.setEntityDesc(entityInfo.getEntityDesc());
            serviceImplInfo.setEntityName(entityInfo.getEntityName());
            serviceImplInfo.setLowerEntityName(
                    entityInfo.getEntityName().substring(0, 1).toLowerCase() + entityInfo.getEntityName().substring(1));
            serviceImplInfo.setPackageStr(Configuration.getString("serviceImpl.package"));
            serviceImplInfo
                    .setServiceType(serviceInfo.getPackageStr() + Constants.CHARACTER_POINT + serviceInfo.getClassName());
            serviceImplInfo.setVoType(voInfos.get(i).getPackageStr() + "." + voInfos.get(i).getClassName());
            serviceImplInfo.setDtoType(dtoInfos.get(i).getPackageStr() + "." + dtoInfos.get(i).getClassName());
            serviceImplInfo.setEntityPackage(serviceInfos.get(i).getEntityPackage());
            serviceImplInfo.setDaoType(daoInfos.get(i).getPackageStr() + "." + daoInfos.get(i).getClassName());
            serviceImplInfo.setVoInfo(voInfos.get(i));
			serviceImplInfo.setDtoInfo(dtoInfos.get(i));
            serviceImplInfo.setServiceInfo(serviceInfo);

            serviceImplInfos.add(serviceImplInfo);
        }
        context.setAttribute("serviceImplInfos", serviceImplInfos);
    }

}
