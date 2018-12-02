package com.alibaba.fastcreate.adapter.impl;

import com.alibaba.fastcreate.model.ClazzModel;
import java.io.File;
import java.io.FileOutputStream;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service("clazzModelAdapter")
public class ClazzModelAdapter {

    public void createFile(ClazzModel clazzModel, String out) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("package " + clazzModel.getPackageHead());

        sb.append(clazzModel.getImportList().stream().collect(Collectors.joining("\n")));

        sb.append(clazzModel.getAnnotationList().stream().collect(Collectors.joining()));

        sb.append(String.format("public class %s {\n", clazzModel.getClazzName()));

        clazzModel.getFieldMap().keySet()
            .forEach(key -> sb.append(String.format("    private %s %s;\n\n", clazzModel.getFieldMap().get(key), key)));

        sb.append("}");

        System.err.println(sb.toString());
        System.err.println("== outputUrl is " + out);
        new FileOutputStream(createOutPath(clazzModel, out))
            .write(sb.toString().getBytes());
    }

    private String createOutPath(ClazzModel clazzModel, String out) {
        if (!out.endsWith(File.separatorChar + "")) {
            out += File.separatorChar;
        }
        return out + clazzModel.getClazzName() + "." + clazzModel.getSuffixName();
    }

}
