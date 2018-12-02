package com.alibaba.fastcreate.proxy;

import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;

public interface CreateProxy {

    List<ClazzModel> createClazzModel(String inputPath) throws Exception;

}
