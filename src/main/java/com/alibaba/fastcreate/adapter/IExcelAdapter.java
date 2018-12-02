package com.alibaba.fastcreate.adapter;

import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;

public interface IExcelAdapter {

    List<ClazzModel> createExcelOutString(String inputPath) throws Exception;

}
