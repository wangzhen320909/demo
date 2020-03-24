package com.example.oracle.demo.task;

import com.example.oracle.demo.DemoApplication;
import com.example.oracle.demo.config.Configuration;
import com.example.oracle.demo.Constants;
import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.model.ColumnInfo;
import com.example.oracle.demo.model.TableInfo;
import com.example.oracle.demo.junit.DbUtil;
import com.example.oracle.demo.junit.FileHelper;
import com.example.oracle.demo.junit.PropertyUtil;
import com.example.oracle.demo.junit.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * 央联支付有限公司
 *
 * @author qiujian
 * @version V1.0
 * @desc
 * @since 2018-04-23
 **/
@Slf4j
public class InitTask extends AbstractApplicationTask {

	@Override
	protected boolean doInternal(ApplicationContext context) throws Exception {
		log.info("初始化任务");

		//首先清空baseDir下的所有文件 位配合mybatis的逆向工程,不清除文件
		String baseDir = Configuration.getString("base.baseDir");
		FileHelper.deleteDirectory(baseDir);

		// 加载属性文件
		// 字段类型与属性类型的映射
		if (Configuration.getString("base.database").equals(Constants.DB_ORACLE)) {
			PropertyUtil.loadProp("columnType2PropType_oracle.properties");
		} else {
			PropertyUtil.loadProp("columnType2PropType.properties");
		}
		// 属性类型与包类名的映射
		PropertyUtil.loadProp("propType2Package.properties");
		// 属性类型与jdbc类型的映射，注意这里为了防止与上面冲突，属性类型前加了_
		PropertyUtil.loadProp("propType2JdbcType.properties");
		// 获取所有需要生成的表名
		List <String> tableList = Arrays.asList(DemoApplication.TABLES);
		log.info("需要生成的表：{}", tableList);

		// 对应的实体名
		List <String> entityNames = new ArrayList <>();
		for (String tableName : tableList) {
			entityNames.add(StringUtil.convertDbName2ClassName(tableName));
		}

		List <String> entityDescs = StringUtil.splitStr2List(Configuration.getString("base.entityDescs"),
				Constants.CHARACTER_COMMA);
		//List<String> entityDescs = new ArrayList<>();
		// 添加映射关系
		Map <String, String> table2Entity = new HashMap <>(20);
		for (int i = 0; i < tableList.size(); i++) {
			table2Entity.put(tableList.get(i), entityNames.get(i));
		}

		// 连接数据库
		Connection conn = null;
		ResultSet tableRS = null;
		ResultSet columnRS = null;
		try {
			conn = DbUtil.getConn();
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String schemaPattern;
			if (Configuration.getString("base.database").equals(Constants.DB_ORACLE)) {
				schemaPattern = Configuration.getString("jdbc.username").toUpperCase();
			} else {
				schemaPattern = Configuration.getString("base.schemaPattern");
			}

			// 获取表的结果集
			if (Configuration.getString("base.database").equals(Constants.DB_ORACLE)) {
				tableRS = dbMetaData.getTables(null, schemaPattern, Constants.PERCENT, new String[]{"TABLE"});
			} else {
				tableRS = dbMetaData.getTables(null, schemaPattern, Constants.EMPTY_STR, new String[]{"TABLE"});
			}

			// 遍历
			Map <String, TableInfo> tableInfos = new HashMap <>(20);
			while (tableRS.next()) {
				// 表名
				String tableName = tableRS.getString("TABLE_NAME").toUpperCase();
				log.info("数据库表名：{}", tableName);

				if (tableList.contains(tableName.toUpperCase())) {
					TableInfo tableInfo = new TableInfo();
					tableInfo.setName(tableName);
					log.info("*****************************");
					log.info("tableName:{}", tableName);
					StringBuilder builder;
					if (Configuration.getString("base.database").equals(Constants.DB_ORACLE)) {
						builder = new StringBuilder("SELECT COMMENTS AS TABLE_COMMENT FROM USER_TAB_COMMENTS WHERE 1=1");
						builder.append(" and table_name = '").append(tableName.toUpperCase()).append("'");
					} else {
						builder = new StringBuilder("SELECT TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='");
						builder.append(DbUtil.getSchema()).append("' and table_name = '").append(tableName).append("'");
					}

					Statement statement = conn.createStatement();
					ResultSet resultSet = statement.executeQuery(builder.toString());
					while (resultSet.next()) {

						String tableComment = resultSet.getString("TABLE_COMMENT");
						// 表注释
						log.info("表{}的注释:{}", tableName, tableComment);
						entityDescs.add(tableComment);
						tableInfo.setRemark(tableComment);
					}
					statement.close();
					// 表类型
					String tableType = tableRS.getString("TABLE_TYPE");
					tableInfo.setType(tableType);
					log.info("表{}的类型:{}", tableName, tableType);

					// 字段
					// 获取列的结果集
					if (Configuration.getString("base.database").equals(Constants.DB_ORACLE)) {
						columnRS = dbMetaData.getColumns(null, schemaPattern, tableName.toUpperCase(), Constants.PERCENT);
					} else {
						columnRS = dbMetaData.getColumns(null, schemaPattern, tableName, Constants.EMPTY_STR);
					}
					List <ColumnInfo> columnList = new ArrayList <>();
					while (columnRS.next()) {
						String columnName = columnRS.getString("COLUMN_NAME").toLowerCase();
						String columnType = columnRS.getString("TYPE_NAME").toLowerCase();
						String columnRemark = columnRS.getString("REMARKS");
						log.info("字段名称：{}, 字段类型：{}, 字段注释：{}", columnName, columnType, columnRemark);

						int len = columnRS.getInt("COLUMN_SIZE");
						//log.info("字段长度：{}", len);

						int precision = columnRS.getInt("DECIMAL_DIGITS");
						//log.info("字段类型精度：{}", precision);

						if (StringUtils.isBlank(columnName)) {
							continue;
						}

						ColumnInfo ci = new ColumnInfo();
						ci.setName(columnName);
						ci.setType(columnType);
						ci.setRemark(columnRemark);
						ci.setLen(len);
						ci.setPrecision(precision);
						columnList.add(ci);
					}
					log.info("*****************************");
					tableInfo.setColumnList(columnList);
					tableInfos.put(tableName, tableInfo);
				}
			}

			// 实体对应的描述
			Map <String, String> entity2Desc = new HashMap <>(20);
			for (int i = 0; i < entityNames.size(); i++) {
				entity2Desc.put(entityNames.get(i), entityDescs.get(i));
			}
			// 放入上下文
			context.setAttribute("tableInfos", tableInfos);
			context.setAttribute("tableName.to.entityName", table2Entity);
			context.setAttribute("entityName.to.desc", entity2Desc);

			if (tableInfos.size() == 0) {
				log.info("在数据库没有匹配到相应的表");
				return true;
			}
		} catch (Exception e) {
			log.info("初始化任务异常", e);

		} finally {
			DbUtil.closeReso(conn, null, tableRS);
			DbUtil.closeReso(null, null, columnRS);
		}
		return false;
	}

}
