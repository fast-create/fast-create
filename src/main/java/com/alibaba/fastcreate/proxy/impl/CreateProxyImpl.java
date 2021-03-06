package com.alibaba.fastcreate.proxy.impl;

import com.alibaba.fastcreate.adapter.IExcelAdapter;
import com.alibaba.fastcreate.exception.BusinessException;
import com.alibaba.fastcreate.model.ClazzModel;
import com.alibaba.fastcreate.proxy.CreateProxy;
import com.alibaba.fastcreate.proxy.XmlManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("createProxy")
public class CreateProxyImpl implements CreateProxy {

    @Autowired
    private IExcelAdapter excelAdapter;
    @Autowired
    private XmlManager xmlAdapter;

    @Override
    public List<ClazzModel> createClazzModel(String inputPath) throws BusinessException {
        try {
            String suffix = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length());
            switch (suffix) {
                case "xlsx":
                case "xls":
                    return excelAdapter.createExcelOutString(inputPath);
                case "xml":
                    return xmlAdapter.createXmlOutString(inputPath);
                default:
                    throw new BusinessException(50, "no inputPath");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(50, e.toString());
        }
    }

}
