package com.example.oracle.demo.task;

import com.example.oracle.demo.framework.AbstractApplicationTask;
import com.example.oracle.demo.framework.context.ApplicationContext;
import com.example.oracle.demo.handler.BaseHandler;
import com.example.oracle.demo.handler.impl.UiHandler;
import com.example.oracle.demo.model.UiInfo;
import com.example.oracle.demo.junit.PropertyUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * 央联支付有限公司
 * @since 2018-04-23
 * @version V1.0
 * @author  qiujian
 * @desc 生产
 **/
@Slf4j
public class UiTask extends AbstractApplicationTask {

    private static String UI_FTL = "templates/Ui.ftl";

    private List<UiInfo> uiInfos;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception {
        log.info("开始生成ui");

        // 获取实体信息
        uiInfos = (List<UiInfo>) context.getAttribute("uiInfos");

        BaseHandler<UiInfo> handler;
        for (UiInfo uiInfo : uiInfos) {
            handler = new UiHandler(UI_FTL, uiInfo);
            handler.execute(context);
        }
        log.info("生成ui完成");
        return false;
    }

    @Override
    protected void doAfter(ApplicationContext context) throws Exception {
        super.doAfter(context);

        for (UiInfo uiInfo : uiInfos) {
			uiInfo = new UiInfo();

        }


    }

    public static void main(String[] args) {
        File file = new File(
                "/D:\\devsoftware\\workspace\\winit-java-generator\\target\\classes\\template\\Ui.ftl");
        System.out.println(UiTask.class.getClassLoader().getResource("").getPath());
        System.out.println(file.exists());

        PropertyUtil.setProperty("name", "qyk1");
        PropertyUtil.setProperty("NAME", "qyk22");
    }

}
