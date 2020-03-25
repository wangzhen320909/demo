package com.example.oracle.demo.task;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.model.ColumnInfo;
import com.example.oracle.demo.model.EntityInfo;
import com.example.oracle.demo.model.TableInfo;
import com.example.oracle.demo.junit.PropertyUtil;
import com.example.oracle.demo.junit.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.Map.Entry;

/**
 * 央联支付有限公司
 *
 * @author wzg
 * @version V1.0
 * @desc
 * @since 2018-04-23
 **/
@Slf4j
public class CombineInfoTask extends AbstractApplicationTask {

	@SuppressWarnings("unchecked")
	@Override
	protected boolean doInternal(ApplicationContext context) throws Exception {
		log.info("组装信息");
		// 获取实体相关的配置
		String packageName = Configuration.getString("entity.package");
		// 存放路径
		String path = Configuration.getString("entity.path");
		log.info("所有实体的包名为{}， 路径为：{}", packageName, path);
		// 获取表和实体的映射集合
		Map <String, String> table2Entities = (Map <String, String>) context.getAttribute("tableName.to.entityName");
		Map <String, String> entity2Desc = (Map <String, String>) context.getAttribute("entityName.to.desc");
		Map <String, TableInfo> tableInfos = (Map <String, TableInfo>) context.getAttribute("tableInfos");

		List <EntityInfo> entityInfos = new ArrayList <>();
		for (Entry <String, String> entry : table2Entities.entrySet()) {
			EntityInfo entityInfo = new EntityInfo();
			// 表名
			String tableName = entry.getKey();
			// 实体名
			String entityName = entry.getValue();
			// 表信息
			TableInfo tableInfo = tableInfos.get(tableName);
			Set <String> imports = new HashSet <>();
			Map <String, String> propTypes = new LinkedHashMap <>();
			Map <String, String> propRemarks = new LinkedHashMap <>();
			Map <String, String> propJdbcTypes = new LinkedHashMap <>();
			Map <String, String> propName2ColumnNames = new LinkedHashMap <>();
			Map <String, Integer> propLength = new LinkedHashMap <>();
			Map <String, String> dicts = new LinkedHashMap <>();

			entityInfo.setTableName(tableName);
			entityInfo.setEntityName(entityName);
			entityInfo.setEntityDesc(entity2Desc.get(entityName));
			entityInfo.setClassName(entityName + Constants.ENTITY_SUFFIX);
			entityInfo.setEntityPackage(packageName);

			if (null == tableInfo) {
				continue;
			}
			// 遍历表字段信息
			List <ColumnInfo> columns = tableInfo.getColumnList();
			for (ColumnInfo columnInfo : columns) {
				String fieldName = columnInfo.getName();
				String fieldType = columnInfo.getType();

				// 通过字段名生成属性名
				String propName = StringUtil.convertFieldName2PropName(fieldName);

				// 这里为了兼容oracle，number类型，如果小数精度为0，则映射成Long类型
				String propType;
				if (Constants.DBTYPE_NUMBER.equals(fieldType) && columnInfo.getPrecision() == 0) {
					if(columnInfo.getLen()==38){
						propType = Constants.PROPTYPE_INTEGER;
					}else{
						propType = Constants.PROPTYPE_LONG;
					}
				}else if (Constants.DBTYPE_NUMBER.equals(fieldType) && columnInfo.getPrecision() > 0) {
					propType = Constants.PROPTYPE_DOUBLE;
				} else {
					propType = PropertyUtil.getValueByKey(fieldType);
				}
				propLength.put(propName, columnInfo.getLen());
				propTypes.put(propName, propType);
				if (null != columnInfo && null != columnInfo.getRemark() && columnInfo.getRemark().contains("[") && columnInfo.getRemark().contains("]")) {
					columnInfo.setDict(columnInfo.getRemark().substring(columnInfo.getRemark().lastIndexOf("[") + 1, columnInfo.getRemark().lastIndexOf("]")));
					columnInfo.setRemark(columnInfo.getRemark().substring(0, columnInfo.getRemark().lastIndexOf("[")));
					dicts.put(propName, columnInfo.getDict());
				}
				propRemarks.put(propName, columnInfo.getRemark());
//                propJdbcTypes.put(propName, PropertyUtil.getValueByKey(Constant.CHARACTER_BOTTOM_LINE + propType));
				propJdbcTypes.put(propName, PropertyUtil.getValueByKey(propType));
				propName2ColumnNames.put(propName, columnInfo.getName().toUpperCase());
			}
			log.info("属性类型：{}", propTypes);
			log.info("属性jdbcTypes：{}", propJdbcTypes);
			// 获取此实体所有的类型
			Collection <String> types = propTypes.values();

			for (String type : types) {
				if (!StringUtil.isEmpty(PropertyUtil.getValueByKey(type))) {
					imports.add(PropertyUtil.getValueByKey(type));
				}
			}
			log.info("imports:{}", imports);
			entityInfo.setPropTypes(propTypes);
			entityInfo.setPropRemarks(propRemarks);
			entityInfo.setPropJdbcTypes(propJdbcTypes);
			entityInfo.setPropNameColumnNames(propName2ColumnNames);
			entityInfo.setImports(imports);
			entityInfo.setPackageClassName(entityInfo.getEntityPackage() + Constants.CHARACTER_POINT + entityInfo.getClassName());
			entityInfo.setEntityDesc(tableInfo.getRemark());
			entityInfo.setPropLength(propLength);
			entityInfo.setDicts(dicts);
			entityInfos.add(entityInfo);

		}

		context.setAttribute("entityInfos", entityInfos);
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Map <String, Object> map1 = new HashMap <>();
		List <Long> list = new ArrayList <>();
		list.add(1L);
		map1.put("list", list);

		List <Long> list2 = (List <Long>) map1.get("list");
		list2.add(2L);

		System.out.println("list:" + list);
		System.out.println("list:" + map1.get("list"));
	}

}
