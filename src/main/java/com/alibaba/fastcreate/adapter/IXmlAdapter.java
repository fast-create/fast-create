package com.alibaba.fastcreate.adapter;

import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;

public interface IXmlAdapter {

    List<ClazzModel> createXmlOutString(String inputPath) throws Exception;

}
