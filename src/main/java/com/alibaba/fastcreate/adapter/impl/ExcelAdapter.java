package com.alibaba.fastcreate.adapter.impl;

import com.alibaba.fastcreate.adapter.IExcelAdapter;
import com.alibaba.fastcreate.model.ClazzModel;
import com.google.common.collect.Lists;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service("excelAdapter")
@Log4j2
public class ExcelAdapter implements IExcelAdapter {

    @Override
    public List<ClazzModel> createExcelOutString(String inputPath) throws Exception {

        Assert.notNull(inputPath, "[inputPath]为空");

        List<ClazzModel> clazzModelList = Lists.newArrayList();

        @Cleanup
        Workbook workbook = WorkbookFactory.create(new FileInputStream(inputPath));
        Iterator<Sheet> sheetIterator = workbook.iterator();
        while (sheetIterator.hasNext()) {
            Sheet nextSheet = sheetIterator.next();

            List<Row> rowList = initRowList(nextSheet);

            ClazzModel clazzModel = new ClazzModel(nextSheet.getSheetName(), "java");

            setPackageInfo(clazzModel, rowList);

            setImportInfo(clazzModel, rowList);

            setFieldMap(clazzModel, rowList);

            setAnnotationList(clazzModel);

            setRequiredProperty(clazzModel, rowList);

            clazzModelList.add(clazzModel);
        }
        return clazzModelList;
    }

    private void setRequiredProperty(ClazzModel clazzModel, List<Row> rowList) {
        Map<String, Boolean> isRequiredMap = rowList.stream()
            .filter(row -> (row.getCell(3) != null && "Y".equalsIgnoreCase(row.getCell(3).toString())))
            .collect(HashMap::new, (m, row) -> m.put(row.getCell(0).toString(), Boolean.TRUE), HashMap::putAll);

        clazzModel.setIsRequiredMap(isRequiredMap);
    }

    private void setAnnotationList(ClazzModel clazzModel) {
        List<String> annotationList = Lists.newArrayList();
        annotationList.add("@Data");
        clazzModel.setAnnotationList(annotationList);
    }

    private List<Row> initRowList(Sheet nextSheet) {
        List<Row> rowList = Lists.newArrayList();
        Iterator<Row> rowIterator = nextSheet.rowIterator();
        while (rowIterator.hasNext()) {
            rowList.add(rowIterator.next());
        }
        Assert.notEmpty(rowList, "[rowList]为空");
        return rowList;
    }

    private void setFieldMap(ClazzModel clazzModel, List<Row> rowList) {
        Map<String, String> fieldMap = rowList.stream().filter(row -> checkIfFieldRow(row, row.getCell(0)))
            .collect(HashMap::new, (m, row) -> m.put(row.getCell(0).toString(), row.getCell(1).toString()),
                HashMap::putAll);
        clazzModel.setFieldMap(fieldMap);

        Map<String, String> fieldAnnotationMap = rowList.stream()
            .filter(row -> checkIfFieldRow(row, row.getCell(0)) && row.getCell(5) != null)
            .collect(HashMap::new, (m, row) -> m.put(row.getCell(0).toString(), row.getCell(5).toString()),
                HashMap::putAll);
        clazzModel.setFieldAnnotationMap(fieldAnnotationMap);

        Map<String, String> fieldRemarkMap = rowList.stream().filter(row -> checkIfFieldRow(row, row.getCell(4)))
            .collect(HashMap::new, (m, row) -> m.put(row.getCell(0).toString(), row.getCell(4).toString()),
                HashMap::putAll);
        clazzModel.setFieldRemarkMap(fieldRemarkMap);

    }

    private void setPackageInfo(ClazzModel clazzModel, List<Row> rowList) {
        clazzModel.setPackageHead(rowList.get(0).getCell(1).toString() + "\n\n");
    }

    private void setImportInfo(ClazzModel clazzModel, List<Row> rowList) {
        Set<String> fieldTypeSet = rowList.stream()
            .filter(row -> checkIfFieldRow(row, row.getCell(0)))
            .map(row -> row.getCell(1).toString()).collect(Collectors.toSet());

        List<String> importList = Lists.newArrayList();
        if (fieldTypeSet.contains("BigDecimal")) {
            importList.add("import java.math.BigDecimal;");
        }
        if (fieldTypeSet.contains("Date")) {
            importList.add("import java.util.Date;");
        }
        if (fieldTypeSet.contains("List")) {
            importList.add("import java.util.List;");
        }
        importList.add("import lombok.Data;");
        clazzModel.setImportList(importList);
    }

    private boolean checkIfFieldRow(Row row, Cell cellVal) {
        return !StringUtils.isEmpty(cellVal) && !row.getCell(0).toString().startsWith("#");
    }

}
