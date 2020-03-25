package com.example.oracle.demo.junit;

import java.text.MessageFormat;

/**
 * 央联支付有限公司
 * @since 2018-05-22
 * @version V1.0
 * @author  wzg
 * @desc    
 **/
public class SysoutUtil {

    public static void printInfo(String str, Object... objects) {
        System.out.println(MessageFormat.format(str, objects));
    }
}
