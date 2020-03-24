package com.example.oracle.demo.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.model.EntityInfo;
import com.example.oracle.demo.model.UiInfo;
import com.example.oracle.demo.model.pojo.ui.UiConfig;
import com.example.oracle.demo.junit.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 央联支付有限公司
 *
 * @author 邱健
 * @version V1.0
 * @create 2018年05月26
 * @desc UiHandler
 **/
public class UiHandler extends BaseHandler <UiInfo> {


	public UiHandler(String ftlName, UiInfo info) {
		this.ftlName = ftlName;
		this.info = info;
		this.savePath = Configuration.getString("base.baseDir") + File.separator
				+ Configuration.getString("ui.path") + File.separator + info.getClassName()
				+ Constants.FILE_SUFFIX_JS;
	}

	/**
	 * 组装参数
	 *
	 * @param info
	 */
	@Override
	public void combileParams(UiInfo info) {

		EntityInfo entityInfo = info.getEntityInfo();
		info.setRequestMapping(entityInfo.getEntityName().substring(0, 1).toLowerCase() + entityInfo.getEntityName().substring(1, entityInfo.getEntityName().length()));

		Map <String, String> propRemarks = entityInfo.getPropRemarks();
		Map <String, String> propTypes = entityInfo.getPropTypes();
		Map <String, String> dicts = entityInfo.getDicts();
		List <UiConfig> modles = new LinkedList <>();
		Set <Map.Entry <String, String>> entries = propRemarks.entrySet();
		for (Map.Entry <String, String> entry : entries) {
			if ("optimistic".equals(entry.getKey())) {
				continue;
			}
			UiConfig uiConfig = new UiConfig();
			uiConfig.setKey(entry.getKey());
			if ("Long".equals(propTypes.get(entry.getKey())) || "Integer".equals(propTypes.get(entry.getKey()))) {
				uiConfig.setType("number");
				uiConfig.setResultType("number");
				uiConfig.setComponent("InputNumber");
			} else if ("Date".equals(propTypes.get(entry.getKey()))) {
				uiConfig.setType("Date");
				uiConfig.setResultType("Date");
				uiConfig.setComponent("DatePicker");
				uiConfig.setFormatDate("YYYY-MM-DD hh:mm:ss");
			} else {
				uiConfig.setType("string");
				uiConfig.setResultType("string");
				uiConfig.setComponent("Input");
			}
			if (StringUtils.isNotBlank(dicts.get(entry.getKey()))) {
				uiConfig.setUseForDict(true);
				uiConfig.setDictKey(dicts.get(entry.getKey()));
				uiConfig.setComponent("Select");
			} else {
				uiConfig.setUseForDict(false);
				uiConfig.setDictKey(null);
			}
			if ("createTime".equals(entry.getKey())) {
				uiConfig.setRangeQuery(true);
			} else {
				uiConfig.setRangeQuery(false);
			}
			if ("id".equals(entry.getKey()) || "createTime".equals(entry.getKey())) {
				if ("createTime".equals(entry.getKey())) {
					uiConfig.setUseForQuery(true);
				} else {
					uiConfig.setUseForQuery(false);
				}
				uiConfig.setUserForAdd(false);
				uiConfig.setUserForUpdate(false);
				uiConfig.setUserForDetails(false);
			} else {
				uiConfig.setUseForQuery(true);
				uiConfig.setUserForAdd(true);
				uiConfig.setUserForUpdate(true);
				uiConfig.setUserForDetails(true);
			}
			uiConfig.setUseForResult(true);
			uiConfig.setRemark(entry.getValue());
			modles.add(uiConfig);
		}

		this.param.put("menuName", StringUtil.convertFieldName2PropName(Configuration.getString("projectInfo.projectName") + "Menu"));
		this.param.put("fileName", StringUtil.lowerFirst(info.getRequestMapping()));
		this.param.put("tableName", info.getRequestMapping());
		this.param.put("serviceName", Configuration.getString("projectInfo.projectName"));
		this.param.put("menuNameRemark", StringUtil.convertFieldName2PropName(Configuration.getString("projectInfo.menuNameRemark")));
		this.param.put("menuItemName", info.getEntityInfo().getEntityDesc());
		this.param.put("model", JSON.toJSONString(modles, SerializerFeature.UseSingleQuotes, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat));

	}
}
