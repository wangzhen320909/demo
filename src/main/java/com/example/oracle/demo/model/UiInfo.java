package com.example.oracle.demo.model;

import lombok.Data;

/**
 * 央联支付有限公司
 *
 * @author 邱健
 * @version V1.0
 * @create 2018年05月26
 * @desc Ui信息
 **/
@Data
public class UiInfo {
	private String className;
	private String requestMapping;
	private EntityInfo entityInfo;
}
