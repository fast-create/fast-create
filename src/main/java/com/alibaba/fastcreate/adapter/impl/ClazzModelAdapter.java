package com.alibaba.fastcreate.adapter.impl;

import com.alibaba.fastcreate.model.ClazzModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("clazzModelAdapter")
@Log4j2
public class ClazzModelAdapter {

    public void createFile(ClazzModel clazzModel, String out) throws Exception {
        StringBuilder sb = new StringBuilder();

        convertIsRequiredMap(clazzModel);

        sb.append("package " + clazzModel.getPackageHead());
        sb.append("\n");

        sb.append(clazzModel.getImportList().stream().collect(Collectors.joining("\n"))).append("\n\n");

        sb.append(clazzModel.getAnnotationList().stream().collect(Collectors.joining("\n"))).append("\n");

        sb.append(String.format("public class %s {\n\n", clazzModel.getClazzName()));

        clazzModel.getFieldMap().forEach((field, type) -> {

            if (StringUtils.hasText(clazzModel.getFieldRemarkMap().get(field))) {
                sb.append(String.format("    /** %s */\n", clazzModel.getFieldRemarkMap().get(field)));
            }

            if (StringUtils.hasText(clazzModel.getFieldAnnotationMap().get(field))) {
                sb.append("    " + clazzModel.getFieldAnnotationMap().get(field) + "\n");
            }
            sb.append(String.format("    private %s %s;\n\n", type, field));
        });

        sb.append("}");

        log.error(sb.toString());
        log.error("== outputUrl is " + out);
        try {
            new FileOutputStream(createOutPath(clazzModel, out)).write(sb.toString().getBytes());
        } finally {

        }
    }

    private void convertIsRequiredMap(ClazzModel clazzModel) {
        if (!StringUtils.isEmpty(clazzModel.getIsRequiredMap())) {
            clazzModel.getImportList().add("import io.swagger.annotations.ApiModelProperty;");

            clazzModel.getIsRequiredMap().forEach((key, flag) -> {
                String annoStr = clazzModel.getFieldAnnotationMap().get(key);
                if (StringUtils.hasText(annoStr)) {
                    if (!annoStr.contains("@ApiModelProperty")) {
                        clazzModel.getFieldAnnotationMap()
                            .put(key, String.format("%s\n    @ApiModelProperty(required = %s)", annoStr, flag));
                    }
                } else {
                    clazzModel.getFieldAnnotationMap()
                        .put(key, String.format("@ApiModelProperty(required = %s)", flag));
                }
            });
        }
    }

    private String createOutPath(ClazzModel clazzModel, String out) {
        if (!out.endsWith(File.separatorChar + "")) {
            out += File.separatorChar;
        }
        return out + clazzModel.getClazzName() + "." + clazzModel.getSuffixName();
    }

}
