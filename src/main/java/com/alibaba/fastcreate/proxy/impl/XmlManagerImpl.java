package com.alibaba.fastcreate.proxy.impl;

import com.alibaba.fastcreate.adapter.ClazzModelBuild;
import com.alibaba.fastcreate.model.ClazzModel;
import com.alibaba.fastcreate.model.xml.FastNode;
import com.alibaba.fastcreate.model.xml.XmlHandler;
import com.alibaba.fastcreate.proxy.XmlManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service("xmlManager")
public class XmlManagerImpl implements XmlManager {

    @Autowired
    @Qualifier("xmlClazzModelAdapter")
    private ClazzModelBuild xmlClazzModelAdapter;

    @Override
    public List<ClazzModel> createXmlOutString(String inputPath) throws Exception {
        List<FastNode> nodeList = initFastNodeList(inputPath);

        return nodeList.stream().filter(fastNode -> fastNode.hasChild())
            .map(fastNode -> xmlClazzModelAdapter.buildModel(nodeList, fastNode)).collect(Collectors.toList());
    }

    private List<FastNode> initFastNodeList(String inputPath)
        throws ParserConfigurationException, SAXException, IOException {
        //创建sax解释器的工厂对象
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();

        //使用sax解释器的工厂对象生产一个sax解释器
        SAXParser saxParser = parserFactory.newSAXParser();

        //使用sax解释器解释xml文件了。
        XmlHandler xmlHandler = new XmlHandler();
        saxParser.parse(new File(inputPath), xmlHandler);
        return xmlHandler.getNodeList();
    }

}
