package com.alibaba.fastcreate.proxy;

import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;

public interface XmlManager {

    List<ClazzModel> createXmlOutString(String inputPath) throws Exception;

}
