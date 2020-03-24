package com.example.oracle.demo.junit;

import com.example.oracle.demo.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * 央联支付有限公司
 * @since 2018-05-22
 * @version V1.0
 * @author  qiujian
 * @desc   属性文件工具类 
 **/
@Slf4j
public class PropertyUtil {

    /**
     * 属性类
     */
    public static Properties properties = new Properties();

    /**
     * loadProp:加载属性文件. <br/>
     *
     * @author qiyongkang
     * @param fileName
     * @since JDK 1.6
     */
    public static void loadProp(String fileName) {
        InputStream is = null;
        Reader reader = null;

        // 首先在classpath中找，如果找不到，则在工作目录下找
        is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        log.info("在classpath下找{},是否找到：{}", fileName, is == null ? "否" : "是");
        if (is == null) {
            // 没找到，则在使用绝对路径，找到jar所在的根目录
//             String rootPath = System.getProperty("user.dir");
             String rootPath = "E:/project/testOracle/src/main/resources";
//            String rootPath = System.getProperty("logDirPath");
            log.info("rootPath:{}", rootPath);

            // 截取一下文件名：BirthdayReminder/生日名单.xls ->
            if (StringUtil.subBySplit(fileName, "/") != null) {
                fileName = StringUtil.subBySplit(fileName, "/");
            } else if (StringUtil.subBySplit(fileName, "\\") != null) {
                fileName = StringUtil.subBySplit(fileName, "\\");
            }

            try {
                is = new FileInputStream(rootPath + File.separator + fileName);
                reader = new InputStreamReader(is, "UTF-8");
                log.info("在classpath下没找到，在用户工作目录下找{},是否找到：{}", fileName, is == null ? "否" : "是");
            } catch (FileNotFoundException e) {

                log.info("获取输入流异常:{}", e);
            } catch (UnsupportedEncodingException e) {

                log.info("不支持的编码异常:{}", e);
            }
        } else {
            try {
                reader = new InputStreamReader(is, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.info("不支持的编码异常:{}", e);
            }
        }

        try {
            properties.load(reader);
            log.info("加载属性文件成功：{}", fileName);
        } catch (IOException e) {
            log.info("加载属性文件异常：{}", fileName);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.info("输入流关闭异常:{}", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.info("字符流关闭异常:{}", e);
                }
            }
        }
    }

    /**
     * loadProp:加载属性文件. <br/>
     *
     * @author qiyongkang
     * @param fileName
     * @since JDK 1.6
     */
    public static void loadPropWithoutLog(String fileName) {
        InputStream is = null;
        Reader reader = null;

        // 首先在classpath中找，如果找不到，则在工作目录下找
        is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        SysoutUtil.printInfo("在classpath下找{0},是否找到：{1}", fileName, is == null ? "否" : "是");
        if (is == null) {
            // 没找到，则在使用绝对路径，找到jar所在的根目录
            // String rootPath = System.getProperty("user.dir");
            String rootPath = System.getProperty("logDirPath");
            SysoutUtil.printInfo("rootPath:{0}", rootPath);

            // 截取一下文件名：BirthdayReminder/生日名单.xls ->
            if (StringUtil.subBySplit(fileName, "/") != null) {
                fileName = StringUtil.subBySplit(fileName, "/");
            } else if (StringUtil.subBySplit(fileName, "\\") != null) {
                fileName = StringUtil.subBySplit(fileName, "\\");
            }

            try {
                is = new FileInputStream(rootPath + File.separator + fileName);
                reader = new InputStreamReader(is, "UTF-8");
                SysoutUtil.printInfo("在classpath下没找到，在用户工作目录下找{0},是否找到：{1}", fileName, is == null ? "否" : "是");
            } catch (FileNotFoundException e) {

                SysoutUtil.printInfo("获取输入流异常:{0}", e);
            } catch (UnsupportedEncodingException e) {

                SysoutUtil.printInfo("不支持的编码异常:{0}", e);
            }
        } else {
            try {
                reader = new InputStreamReader(is, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                SysoutUtil.printInfo("不支持的编码异常:{0}", e);
            }
        }

        try {
            properties.load(reader);
            SysoutUtil.printInfo("加载属性文件成功：{0}", fileName);
        } catch (IOException e) {
            SysoutUtil.printInfo("加载属性文件异常：{0}", fileName);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    SysoutUtil.printInfo("输入流关闭异常:{0}", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                    SysoutUtil.printInfo("字符流关闭异常:{0}", e);
                }
            }
        }
    }

    /**
     * getInputStremByFileName:根据文件名获取输入流. <br/>
     *
     * @author qiyongkang
     * @param fileName
     * @return
     * @since JDK 1.6
     */
    public static InputStream getInputStremByFileName(String fileName) {
        InputStream is = null;
        // 首先在classpath中找，如果找不到，则在工作目录下找
        is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        log.info("在classpath下找{},是否找到：{}", fileName, is == null ? "否" : "是");

        if (is == null) {
            // 没找到，则在使用绝对路径，找到jar所在的根目录
            // String rootPath = System.getProperty("user.dir");
            String rootPath = System.getProperty("logDirPath");
            log.info("rootPath:{}", rootPath);

            // 截取一下文件名：BirthdayReminder/生日名单.xls ->
            if (StringUtil.subBySplit(fileName, "/") != null) {
                fileName = StringUtil.subBySplit(fileName, "/");
            } else if (StringUtil.subBySplit(fileName, "\\") != null) {
                fileName = StringUtil.subBySplit(fileName, "\\");
            }

            try {
                is = new FileInputStream(rootPath + File.separator + fileName);
                log.info("在classpath下没找到，在用户工作目录下找{},是否找到：{}", fileName, is == null ? "否" : "是");
            } catch (FileNotFoundException e) {

                log.info("获取输入流异常");
            }
        }
        return is;
    }

    /**
     * getInputStremByFileName:根据文件名获取输入流. <br/>
     *
     * @author qiyongkang
     * @param fileName
     * @return
     * @since JDK 1.6
     */
    public static Reader getReaderByFileName(String fileName) {
        InputStream is = null;
        Reader reader = null;
        // 首先在classpath中找，如果找不到，则在工作目录下找
        is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        log.info("在classpath下找{},是否找到：{}", fileName, is == null ? "否" : "是");

        if (is == null) {
            // 没找到，则在使用绝对路径，找到jar所在的根目录
            // String rootPath = System.getProperty("user.dir");
            String rootPath = System.getProperty("logDirPath");
            log.info("rootPath:{}", rootPath);

            // 截取一下文件名：BirthdayReminder/生日名单.xls ->
            if (StringUtil.subBySplit(fileName, "/") != null) {
                fileName = StringUtil.subBySplit(fileName, "/");
            } else if (StringUtil.subBySplit(fileName, "\\") != null) {
                fileName = StringUtil.subBySplit(fileName, "\\");
            }

            try {
                is = new FileInputStream(rootPath + File.separator + fileName);
                reader = new InputStreamReader(is, "UTF-8");
                log.info("在classpath下没找到，在用户工作目录下找{},是否找到：{}", fileName, is == null ? "否" : "是");
            } catch (FileNotFoundException e) {

                log.info("获取输入流异常");
            } catch (UnsupportedEncodingException e) {

            }
        }
        return reader;
    }

    /**
     * getValueByKey:通过key取对应的值. <br/>
     *
     * @author qiyongkang
     * @param key
     * @return
     * @since JDK 1.6
     */
    public static String getValueByKey(String key) {
        String value = Constants.EMPTY_STR;
        if (key != null && key.length() > 0) {
            try {
                // 首先在系统属性中获取，如果没有，则在配置中获取
                if (!StringUtil.isEmpty(System.getProperty(key))) {
                    value = System.getProperty(key);
                    // log.info("在系统属性中获取{}的value为：{}", key, value);
                } else {
                    value = properties.getProperty(key);
                    // log.info("在配置文件中获取{}的value为：{}", key, value);
                }
            } catch (Exception e) {

            }
        }
        return value;
    }

    /**
     * getValueByKey:通过key取对应的值. <br/>
     *
     * @author qiyongkang
     * @param key
     * @return
     * @since JDK 1.6
     */
    public static String getValueByKeyWithoutLog(String key) {
        String value = Constants.EMPTY_STR;
        if (key != null && key.length() > 0) {
            try {
                // 首先在系统属性中获取，如果没有，则在配置中获取
                if (!StringUtil.isEmpty(System.getProperty(key))) {
                    value = System.getProperty(key);
                    SysoutUtil.printInfo("在系统属性中获取{0}的value为：{1}", key, value);
                } else {
                    value = properties.getProperty(key);
                    SysoutUtil.printInfo("在配置文件中获取{0}的value为：{1}", key, value);
                }
            } catch (Exception e) {

            }
        }
        return value;
    }

    /**
     * setProperty:设置一个属性. <br/>
     *
     * @author qiyongkang
     * @param key
     * @param value
     * @since JDK 1.6
     */
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * getConfigurationPath: 获取配置路径. <br/>
     * 这个方法可以获取jar文件所在文件夹的路径，不包含lib包。
     * <p/>
     * 
     * @author qiyongkang
     * @return 配置路径对象
     * @since JDK 1.6
     */
    public static String getConfigurationPath() {
        String path = PropertyUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        if (!path.endsWith("/")) {
            path = path.substring(0, path.lastIndexOf("/") + 1);
            SysoutUtil.printInfo("jar文件所在文件夹的路径:{0}", path);
        } else {
            SysoutUtil.printInfo("classpath路径:{0}", path);
        }

        // String libDir = "/" + "lib" + "/";
        // if (path.endsWith(libDir)) {
        // path = path.replaceAll(libDir, "/");
        // }
        return path;
    }

    public static void main(String[] args) {
        // loadProp("jdbc.properties");
        // System.out.println(PropertyUtil.getValueByKey("driver"));
        // loadProp("system.properties");
        // File f = new File("E:/武汉/张伟/以图搜图/有匹配排重(整理后)/SUV有匹配");
        // boolean isSuccess = f.mkdirs();
        // System.out.println(isSuccess);
        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);
    }
}
