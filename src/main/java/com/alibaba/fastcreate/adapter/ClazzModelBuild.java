package com.alibaba.fastcreate.adapter;

import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;

public interface ClazzModelBuild {

    ClazzModel buildModel(List list, Object object);

    void init(List list, Object object);

}
