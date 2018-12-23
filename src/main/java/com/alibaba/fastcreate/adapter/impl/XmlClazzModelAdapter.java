package com.alibaba.fastcreate.adapter.impl;


import com.alibaba.fastcreate.adapter.ClazzModelBuild;
import com.alibaba.fastcreate.model.ClazzModel;
import com.alibaba.fastcreate.model.xml.FastNode;
import com.alibaba.fastcreate.proxy.BaseClazzModelBuilder;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service("xmlClazzModelAdapter")
public class XmlClazzModelAdapter extends BaseClazzModelBuilder implements ClazzModelBuild {

    private List<FastNode> fastNodeList;
    private FastNode fastNode;

    @Override
    public ClazzModel buildModel(List list, Object object) {
        init(list, object);
        return build();
    }

    @Override
    public void init(List list, Object object) {
        this.fastNodeList = list;
        this.fastNode = (FastNode) object;
    }


    @Override
    protected void setAnnotationList(ClazzModel clazzModel) {
        List<String> annotationList = Lists.newArrayList();
        annotationList.add("@XmlAccessorType(XmlAccessType.FIELD)");
        annotationList.add(String.format("@XmlType(name = \"\", propOrder = {\"%s\"})",
            fastNodeList.stream().filter(
                node -> checkIfChild(fastNode, node))
                .map(FastNode::getNodeName).collect(Collectors.joining("\", \""))));

        if (fastNode.getNodeLevel() == 0) {
            annotationList.add(String.format("@XmlRootElement(name = \"%s\")", fastNode.getNodeLocalName()));
        }
        clazzModel.setAnnotationList(annotationList);
    }

    @Override
    protected void setFieldMap(ClazzModel clazzModel) {
        fastNodeList.stream().filter(node -> checkIfChild(fastNode, node)).forEach(node -> {
            if (node.hasChild()) {
                clazzModel.getFieldMap().put(node.getNodeName(), node.getClassName());
            } else {
                clazzModel.getFieldMap().put(node.getNodeName(), "String");
            }
        });
    }

    @Override
    protected void setFieldAnnotations(ClazzModel clazzModel) {
        fastNodeList.stream().filter(node -> checkIfChild(fastNode, node)).forEach(node ->
            clazzModel.getFieldAnnotationMap().put(node.getNodeName(),
                String.format("@XmlElement(name = \"%s\", required = true)", node.getNodeLocalName()))
        );
    }

    @Override
    protected void setImportInfo(ClazzModel clazzModel) {
        List<String> importList = Lists.newArrayList();
        importList.add("import javax.xml.bind.annotation.XmlAccessType;");
        importList.add("import javax.xml.bind.annotation.XmlAccessorType;");
        if (fastNode.getNodeLevel() == 0) {
            importList.add("import javax.xml.bind.annotation.XmlRootElement;");
        }

        importList.add("import javax.xml.bind.annotation.XmlType;");
        importList.add("import javax.xml.bind.annotation.XmlElement;");
        clazzModel.setImportList(importList);
    }

    @Override
    protected void setPackageInfo(ClazzModel clazzModel) {
        clazzModel.setPackageHead("com.alibaba.fastcreate;");
        clazzModel.setClazzName(fastNode.getClassName());
    }

    private boolean checkIfChild(FastNode fastNode, FastNode node) {
        return node.getNodeLevel() == fastNode.getNodeLevel() + 1 && node.getParentNodeId()
            .equals(fastNode.getNodeId());
    }

}
