package com.example.oracle.demo.model;

import lombok.Data;

@Data
public class ColumnInfo {

    private String name;
    private String type;
    private String remark;
    private int len;
    private int precision;
    private String jdbcType;
    private String dict;

}
