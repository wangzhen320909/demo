package com.example.oracle.demo.handler;

import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.junit.DateUtil;
import com.example.oracle.demo.junit.FileHelper;
import com.example.oracle.demo.junit.FreeMarkerUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 央联支付有限公司
 * @since 2018-05-22
 * @version V1.0
 * @author  qiujian
 * @desc
 **/
public abstract class BaseHandler<T> {

    protected ApplicationContext context;
    protected String ftlName;
    protected String savePath;
    protected Map<String, String> param = new HashMap<>();
    protected T info;

    public String generateFinalStr() {
        String temp = FileHelper.readFileToString(this.getClass().getClassLoader().getResource("").getPath() + ftlName);
        return FreeMarkerUtil.getProcessValue(param, temp);
    }

    /**
     * 保存到文件
     *
     * @param str
     */
    public void saveToFile(String str) {
        FileHelper.writeToFile(savePath, str);
    }

    /**
     * 组装参数
     *
     * @param info
     */
    public abstract void combileParams(T info);

    /**
     * 设置一些公共的参数.
     */
    public void beforeGenerate() {
        String time = DateUtil.formatDataToStr(new Date(), "yyyy年MM月dd日");
        param.put("author", System.getProperty("user.name"));
        param.put("time", time);
    }

    /**
     * 生成文件
     */
    public void execute(ApplicationContext context) {
        this.context = context;
        String str = null;
        combileParams(info);
        beforeGenerate();
        str = generateFinalStr();
        saveToFile(str);
    }

}
