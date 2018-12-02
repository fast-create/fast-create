package com.alibaba.fastcreate.model;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClazzModel extends BaseModel {

    private static final long serialVersionUID = 2094315089494301875L;

    private String packageHead;

    private List<String> importList;

    private List<String> annotationList;

    private String clazzName;

    /**
     * 文件后缀名
     */
    private String suffixName;

    /**
     * 字段名
     * key eg.field1
     * value eg.String
     */
    private Map<String, String> fieldMap;

    private Map<String, String> fieldAnnotationMap;

    public ClazzModel() {
    }


    public ClazzModel(String clazzName, String suffixName) {
        this.clazzName = clazzName;
        this.suffixName = suffixName;
    }
}

