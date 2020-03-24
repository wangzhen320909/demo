package com.example.oracle.demo.task;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.EntityHandler;
import com.example.oracle.demo.model.*;
import com.example.oracle.demo.junit.PropertyUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 央联支付有限公司
 *
 * @author qiujian
 * @version V1.0
 * @desc
 * @since 2018-04-23
 **/
@Slf4j
public class EntityTask extends AbstractApplicationTask {

	private static String ENTITY_FTL = "templates/Entity.ftl";

	private List <EntityInfo> entityInfos;


	@SuppressWarnings("unchecked")
	@Override
	protected boolean doInternal(ApplicationContext context) throws Exception {
		log.info("开始生成实体");

		// 获取实体信息
		entityInfos = (List <EntityInfo>) context.getAttribute("entityInfos");

		BaseHandler <EntityInfo> handler;
		for (EntityInfo entityInfo : entityInfos) {
			handler = new EntityHandler(ENTITY_FTL, entityInfo);
			handler.execute(context);
		}
		log.info("生成实体类完成");
		return false;
	}

	@Override
	protected void doAfter(ApplicationContext context) throws Exception {
		super.doAfter(context);
		List <DaoInfo> daoList = new ArrayList <>();
		List <VoInfo> voList = new ArrayList <>();
		List <DtoInfo> dtoList = new ArrayList <>();
		List <ServiceInfo> serviceList = new ArrayList <>();
		List <UiInfo> uiInfos = new ArrayList <>();
		// 组装Dao信息、组装Vo信息
		DaoInfo daoInfo;
		VoInfo voInfo;
		DtoInfo dtoInfo;
		ServiceInfo serviceInfo;
		UiInfo uiInfo;
		for (EntityInfo entityInfo : entityInfos) {
			voInfo = new VoInfo();
			voInfo.setPackageStr(Configuration.getString("vo.package"));
			voInfo.setClassName(entityInfo.getEntityName() + Constants.VO_SUFFIX);
			voInfo.setEntityInfo(entityInfo);
			voList.add(voInfo);

			dtoInfo = new DtoInfo();
			dtoInfo.setPackageStr(Configuration.getString("dto.package"));
			dtoInfo.setClassName(entityInfo.getEntityName() + Constants.DTO_SUFFIX);
			dtoInfo.setEntityInfo(entityInfo);
			dtoList.add(dtoInfo);

			daoInfo = new DaoInfo();
			daoInfo.setClassName(entityInfo.getEntityName() + Constants.MAPPER_XML_SUFFIX);
			daoInfo.setEntityInfo(entityInfo);
			daoInfo.setPackageStr(Configuration.getString("dao.package"));
			daoInfo.setVoInfo(voInfo);
			daoList.add(daoInfo);

			serviceInfo = new ServiceInfo();
			serviceInfo.setPackageStr(Configuration.getString("service.package"));
			serviceInfo.setClassName(entityInfo.getEntityName() + Constants.SERVICE_SUFFIX);
			serviceInfo.setEntityDesc(entityInfo.getEntityDesc());
			serviceInfo.setEntityName(entityInfo.getEntityName());
			serviceInfo.setEntityPackage(entityInfo.getEntityPackage());
			serviceInfo.setVoInfo(voInfo);
			serviceList.add(serviceInfo);

			uiInfo = new UiInfo();
			uiInfo.setEntityInfo(entityInfo);
			uiInfo.setClassName(entityInfo.getEntityName() + Constants.UI_SUFFIX);
			uiInfos.add(uiInfo);
		}

		context.setAttribute("daoList", daoList);
		context.setAttribute("voList", voList);
		context.setAttribute("dtoList", dtoList);
		context.setAttribute("serviceInfos", serviceList);
		context.setAttribute("uiInfos", uiInfos);
	}

	public static void main(String[] args) {
		File file = new File(
				"/D:\\devsoftware\\workspace\\winit-java-generator\\target\\classes\\template\\Entity.ftl");
		System.out.println(EntityTask.class.getClassLoader().getResource("").getPath());
		System.out.println(file.exists());

		PropertyUtil.setProperty("name", "qyk1");
		PropertyUtil.setProperty("NAME", "qyk22");
	}

	{
	}
}
