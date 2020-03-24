package com.example.oracle.demo.task;

import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.DtoHandler;
import com.example.oracle.demo.model.DtoInfo;
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
public class DtoTask extends AbstractApplicationTask {

    private static String  DTO_FTL = "templates/Dto.ftl";
    private List<DtoInfo> dtoList;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成dto");
        dtoList = (List<DtoInfo>) context.getAttribute("dtoList");

        BaseHandler<DtoInfo> handler;
        for (DtoInfo dtoInfo : dtoList) {
            handler = new DtoHandler(DTO_FTL, dtoInfo);
            handler.execute(context);
        }
        log.info("结束生成dto");
        return false;
    }

}
