package com.example.oracle.demo.task;

import com.example.oracle.demo.Constants;
import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.DaoHandler;
import com.example.oracle.demo.model.DaoInfo;
import com.example.oracle.demo.model.MapperXmlInfo;
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
public class DaoTask extends AbstractApplicationTask {

    private static String DAO_FTL = "templates/Dao.ftl";

    private List<DaoInfo> daoInfos;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成Dao");

        // 获取实体信息
        daoInfos = (List<DaoInfo>) context.getAttribute("daoList");

        BaseHandler<DaoInfo> handler;
        for (DaoInfo daoInfo : daoInfos) {
            handler = new DaoHandler(DAO_FTL, daoInfo);
            handler.execute(context);
        }
        log.info("生成Dao完成");
        return false;
    }

    @Override
    protected void doAfter(ApplicationContext context) throws Exception {
        super.doAfter(context);
        List<MapperXmlInfo> mapperXmlInfos = new ArrayList<>();
        // 组装Dao信息、组装Vo信息
        MapperXmlInfo mapperXmlInfo;
        for (DaoInfo daoInfo : daoInfos) {
            mapperXmlInfo = new MapperXmlInfo();
            mapperXmlInfo.setNameSpace(daoInfo.getPackageStr() + "." + daoInfo.getClassName());
            mapperXmlInfo.setEntityInfo(daoInfo.getEntityInfo());
            mapperXmlInfo.setVoInfo(daoInfo.getVoInfo());
            mapperXmlInfo.setClassName(daoInfo.getEntityInfo().getEntityName() + Constants.MAPPER_XML_SUFFIX);
            mapperXmlInfos.add(mapperXmlInfo);
        }

        context.setAttribute("mapperXmlList", mapperXmlInfos);
    }

}
