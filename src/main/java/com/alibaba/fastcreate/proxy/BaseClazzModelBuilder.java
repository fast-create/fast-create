package com.alibaba.fastcreate.proxy;

import com.alibaba.fastcreate.model.ClazzModel;

/**
 * @author mengjunya
 */
public abstract class BaseClazzModelBuilder {

    public final ClazzModel build() {

        ClazzModel clazzModel = new ClazzModel();

        setPackageInfo(clazzModel);

        setImportInfo(clazzModel);

        setAnnotationList(clazzModel);

        setFieldMap(clazzModel);

        setFieldAnnotations(clazzModel);

        return clazzModel;
    }

    protected abstract void setPackageInfo(ClazzModel clazzModel);

    protected abstract void setImportInfo(ClazzModel clazzModel);

    protected abstract void setAnnotationList(ClazzModel clazzModel);

    protected abstract void setFieldMap(ClazzModel clazzModel);

    protected abstract void setFieldAnnotations(ClazzModel clazzModel);


}
