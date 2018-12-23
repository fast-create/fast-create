package com.alibaba.fastcreate.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengjunya
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClazzModel", description = "封装的类对象属性对象")
public class ClazzModel extends BaseModel {

    private static final long serialVersionUID = 2094315089494301875L;

    @ApiModelProperty(value = "类的包信息")
    private String packageHead;

    @ApiModelProperty(value = "import列表", dataType = "List")
    private List<String> importList = Lists.newArrayList();

    @ApiModelProperty(value = "类的注解信息", dataType = "List")
    private List<String> annotationList = Lists.newArrayList();

    @ApiModelProperty(value = "类名", required = true)
    private String clazzName;

    /**
     * 文件后缀名
     */
    @ApiModelProperty(value = "后缀名，如java", required = true)
    private String suffixName = "java";

    /**
     * 字段名 key eg.field1 value eg.String
     */
    @ApiModelProperty(value = "字段信息,key-字段名;value-字段类型", dataType = "Map")
    private Map<String, String> fieldMap = Maps.newHashMap();

    @ApiModelProperty(value = "字段的注解信息", dataType = "Map")
    private Map<String, String> fieldAnnotationMap = Maps.newHashMap();

    @ApiModelProperty(value = "字段的备注信息", dataType = "Map")
    private Map<String, String> fieldRemarkMap = Maps.newHashMap();

    @ApiModelProperty(value = "字段的是否必填属性", dataType = "Map")
    private Map<String, Boolean> isRequiredMap = Maps.newHashMap();

    public ClazzModel() {
    }


    public ClazzModel(String clazzName, String suffixName) {
        this.clazzName = clazzName;
        this.suffixName = suffixName;
    }
}

