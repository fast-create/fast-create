package com.alibaba.fastcreate.adapter.impl;

import com.alibaba.fastcreate.adapter.IXmlAdapter;
import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;
import org.springframework.stereotype.Service;

@Service("xmlAdapter")
public class XmlAdapter implements IXmlAdapter {

    @Override
    public List<ClazzModel> createXmlOutString(String inputPath) throws Exception {

        return null;
    }

}
