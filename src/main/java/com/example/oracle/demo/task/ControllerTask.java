package com.example.oracle.demo.task;

import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.ControllerHandler;
import com.example.oracle.demo.model.ControllerInfo;
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
public class ControllerTask extends AbstractApplicationTask {

    private static String CONTROLLER_FTL = "templates/Controller.ftl";

    private List<ControllerInfo> controllerInfos = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成Controller...");
        controllerInfos = (List<ControllerInfo>) context.getAttribute("controllerList");

        BaseHandler<ControllerInfo> baseHandler;
        for (ControllerInfo info : controllerInfos) {
            baseHandler = new ControllerHandler(CONTROLLER_FTL, info);
            baseHandler.execute(context);
        }

        log.info("结束生成Controller...");
        return false;
    }
}
