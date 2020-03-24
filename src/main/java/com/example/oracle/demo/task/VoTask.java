package com.example.oracle.demo.task;

import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.VoHandler;
import com.example.oracle.demo.model.VoInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 央联支付有限公司
 * @since 2018-04-23
 * @version V1.0
 * @author  qiujian
 * @desc
 **/
@Slf4j
public class VoTask extends AbstractApplicationTask {

    private static String VO_FTL = "templates/Vo.ftl";
    private List<VoInfo> voList;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成vo");
        voList = (List<VoInfo>) context.getAttribute("voList");

        BaseHandler<VoInfo> handler;
        for (VoInfo voInfo : voList) {
            handler = new VoHandler(VO_FTL, voInfo);
            handler.execute(context);
        }
        log.info("结束生成vo");
        return false;
    }

}
