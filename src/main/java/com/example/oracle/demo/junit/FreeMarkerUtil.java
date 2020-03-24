package com.example.oracle.demo.junit;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class FreeMarkerUtil {

    public static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setObjectWrapper(Configuration.getDefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(StringUtil.class, "/");
    }

    /**
     * 获取解析后的值.
     *
     * @param param
     * @param temp
     * @return
     */
    public static String getProcessValue(Map<String, String> param, String temp) {
        try {
            Template template = new Template("",
                    new StringReader("<#escape x as (x)!>" + temp + "</#escape>"),
                    configuration);
            StringWriter sw = new StringWriter();
            template.process(param, sw);
            return sw.toString();
        } catch (Exception e) {

            return null;
        }
    }
}
