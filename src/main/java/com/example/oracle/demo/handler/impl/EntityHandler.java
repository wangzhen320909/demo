package com.example.oracle.demo.handler.impl;

import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.model.EntityInfo;
import com.example.oracle.demo.junit.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 央联支付有限公司
 *
 * @author qiujian
 * @version V1.0
 * @desc
 * @since 2018-04-23
 **/
public class EntityHandler extends BaseHandler <EntityInfo> {

	public EntityHandler(String ftlName, EntityInfo info) {
		this.ftlName = ftlName;
		this.info = info;
		this.savePath = Configuration.getString("base.baseDir") + File.separator
				+ Configuration.getString("entity.path") + File.separator + info.getClassName()
				+ Constants.FILE_SUFFIX_JAVA;

	}

	@Override
	public void combileParams(EntityInfo entityInfo) {
		this.param.put("packageStr", entityInfo.getEntityPackage());
		this.param.put("enumsPackageStr", entityInfo.getEntityPackage().replace("po", "enums.*"));
		StringBuilder sb = new StringBuilder();
		for (String str : entityInfo.getImports()) {
			sb.append("import ").append(str).append(";\r\n");
		}
		this.param.put("importStr", sb.toString());
		this.param.put("entityDesc", entityInfo.getEntityDesc());
		this.param.put("className", entityInfo.getClassName());

		// 生成属性，getter,setter方法
		sb = new StringBuilder();
		Map <String, String> propRemarks = entityInfo.getPropRemarks();
		Map <String, Integer> propLength = entityInfo.getPropLength();
		Map <String, String> propNameColumnNames = entityInfo.getPropNameColumnNames();
		Map <String, String> dicts = entityInfo.getDicts();
		int charIndex = 65;
		int j = 65;
		for (Entry <String, String> entry : entityInfo.getPropTypes().entrySet()) {
			String propName = entry.getKey();
			String propType = entry.getValue();
			// 注释、类型、名称
			if ("id".equalsIgnoreCase(propName)) {
				sb.append("    @Id\r\n");
				if (Configuration.getString("base.database").equals(Constants.DB_ORACLE)) {
					sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = \"SELECT " + entityInfo.getTableName() + "_SEQ.NEXTVAL FROM DUAL\")\r\n");

				} else {
					sb.append("    @GeneratedValue(generator = \"JDBC\")\r\n");
				}
			} else {
				sb.append("    @Column(name = \"");
				sb.append(propNameColumnNames.get(propName));
				sb.append("\")\r\n");
			}
			sb.append("    @ApiModelProperty(value = \"");
			sb.append(propRemarks.get(propName));

			sb.append("\", dataType = \"" + propType);
			sb.append("\"");
			if ("id".equals(propName) || "optimistic".equals(propName) || "createTime".equals(propName)) {
				sb.append(", hidden = true");
			}
			sb.append(")\r\n");

			if ( "createTime".equals(propName)) {
				sb.append("    @MyBatisPluginPlus(isQueryCol = false, orderByClause = \"DESC\")").append("\r\n");
			}


			if ("String".equals(propType) && StringUtils.isBlank(dicts.get(propName))) {
				sb.append("    @Length(max = ")
						.append(propLength.get(propName))
						.append(", message = \"{" + entityInfo.getClassName().toLowerCase() + "." + propName + ".length" + "}\")\r\n");
			}
			if (!"id".equals(propName) && !"optimistic".equals(propName)) {
				sb.append("    @ExcelField(name = \"")
						.append(propRemarks.get(propName));
				if ("Date".equals(propType)) {
					sb.append("\", formatDate = \"yyyy-MM-dd HH:mm:ss");
				}
				if (StringUtils.isNotBlank(dicts.get(propName))) {
					sb.append("\", dict = \"" + dicts.get(propName));
				}

				/*列*/
				char columnChar = (char) charIndex++;
				String columnStr = "";
				if (charIndex > 91) {
					columnStr = "A" + String.valueOf((char) (j++));
				}
				columnStr = StringUtils.isNotBlank(columnStr) ? columnStr : String.valueOf(columnChar);
				sb.append("\", columnWidth = \"20")
						.append("\", column = \"" + columnStr + "\")\r\n");

			}

			sb.append("    private ");
			if (StringUtils.isNotBlank(dicts.get(propName))) {
				//字典类型用枚举
				sb.append(StringUtil.upperFirst(StringUtil.convertFieldName2PropName(dicts.get(propName).toLowerCase())));
			} else {
				sb.append(propType);
			}
			sb.append(" ")
					.append(propName)
					.append(";\r\n\r\n");
		}

		this.param.put("propertiesStr", sb.toString());
		this.param.put("serialVersionNum", StringUtil.generate16LongNum());
		this.param.put("tableName", entityInfo.getTableName());
	}
}