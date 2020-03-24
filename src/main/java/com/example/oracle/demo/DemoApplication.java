package com.example.oracle.demo;

import com.example.oracle.demo.application.CodeMakerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {


    /**
     * 需要生成代码的表名数组
     */
    public static final String[] TABLES = {
            "TEST_W"
    };


    public static void main(String[] args) {
        //生成代码
        CodeMakerApplication.doCodeMaker(args);
//        SpringApplication.run(DemoApplication.class, args);
    }

}
