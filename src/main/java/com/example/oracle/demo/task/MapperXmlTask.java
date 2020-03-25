package com.example.oracle.demo.task;

import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.MapperXmlHandler;
import com.example.oracle.demo.model.MapperXmlInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 央联支付有限公司
 * @since 2018-04-23
 * @version V1.0
 * @author  wzg
 * @desc
 **/
@Slf4j
public class MapperXmlTask extends AbstractApplicationTask {

    private static String MAPPER_XML_FTL = "templates/MapperXml.ftl";

    private List<MapperXmlInfo> mapperXmlInfos;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成MapperXml");

        // 获取实体信息
        mapperXmlInfos = (List<MapperXmlInfo>) context.getAttribute("mapperXmlList");

        BaseHandler<MapperXmlInfo> handler;
        for (MapperXmlInfo mapperXmlInfo : mapperXmlInfos) {
            handler = new MapperXmlHandler(MAPPER_XML_FTL, mapperXmlInfo);
            handler.execute(context);
        }
        log.info("生成MapperXml完成");
        return false;
    }

}
