package com.example.oracle.demo.task;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.ServiceImplHandler;
import com.example.oracle.demo.model.ControllerInfo;
import com.example.oracle.demo.model.ServiceImplInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 央联支付有限公司
 * @since 2018-04-23
 * @version V1.0
 * @author  qiujian
 * @desc
 **/
@Slf4j
public class ServiceImplTask extends AbstractApplicationTask {

    private static String SERVICEIMPL_FTL = "templates/ServiceImpl.ftl";

    private List<ServiceImplInfo> serviceImplInfos = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成serviceImpl...");
        serviceImplInfos = (List<ServiceImplInfo>) context.getAttribute("serviceImplInfos");

        BaseHandler<ServiceImplInfo> baseHandler;
        for (ServiceImplInfo info : serviceImplInfos) {
            baseHandler = new ServiceImplHandler(SERVICEIMPL_FTL, info);
            baseHandler.execute(context);
        }

        log.info("结束生成serviceImpl...");
        return false;
    }

    @Override
    protected void doAfter(ApplicationContext context) throws Exception {
        super.doAfter(context);
        List<ControllerInfo> controllerList = new ArrayList<>();
        // 组装Controller信息
        ControllerInfo controllerInfo;
        for (ServiceImplInfo serviceImplInfo : serviceImplInfos) {
            controllerInfo = new ControllerInfo();

            controllerInfo.setClassName(serviceImplInfo.getEntityName() + Constants.CONTROLLER_SUFFIX);
            controllerInfo.setPackageStr(Configuration.getString("controller.package"));
            controllerInfo.setVoInfo(serviceImplInfo.getVoInfo());
            controllerInfo.setDtoInfo(serviceImplInfo.getDtoInfo());
            controllerInfo.setServiceInfo(serviceImplInfo.getServiceInfo());
            String entityName = serviceImplInfo.getEntityName();
            controllerInfo.setEntityName(entityName);
            controllerInfo.setRequestMapping(entityName.substring(0, 1).toLowerCase() + entityName.substring(1, entityName.length()));
            controllerInfo.setEntityPackage(serviceImplInfo.getEntityPackage());
            controllerInfo.setEntityDesc(serviceImplInfo.getEntityDesc());
            controllerList.add(controllerInfo);
        }

        context.setAttribute("controllerList", controllerList);
    }
}
