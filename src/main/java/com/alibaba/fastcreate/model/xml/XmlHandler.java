package com.alibaba.fastcreate.model.xml;

import com.alibaba.fastcreate.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Data
public class XmlHandler extends DefaultHandler {

    private StringBuilder sb = new StringBuilder();
    private FastNode fastNode;
    private int level = 0;

    private List<FastNode> nodeList = new ArrayList();

    /**
     * 当sax解释器读到了开始标签的时候会调用该方法。 localName:当前的标签名字 attributes:把这个标签的所有属性存储到这个集合中。
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        FastNode currentNode = new FastNode();
        currentNode.setNodeLevel(this.level);
        currentNode.setNodeLocalName(qName);
        currentNode.setNodeName(StringUtils.underlineToCamel(qName.toLowerCase()));
        currentNode.setNodeType("String");
        currentNode.setNodeId(UUID.randomUUID().toString());
        if (level > 0) {
            String parentNodeName = sb.substring(sb.lastIndexOf("<") + 1, sb.length() - 1);

//            currentNode.setParentNodeId(nodeList.stream()
//                .filter(node -> node.getNodeLocalName().equals(parentNodeName) && node.getNodeLevel() == this.level - 1)
//                .findFirst().get().getNodeId());

            for (FastNode node : nodeList) {
                if (node.getNodeLocalName().equals(parentNodeName) && node.getNodeLevel() == this.level - 1) {
                    currentNode.setParentNodeId(node.getNodeId());
                    if (!this.nodeList.contains(currentNode)) {
                        node.getChildNodes().add(currentNode.getNodeId());
                    }
                    break;
                }
            }
        }

        if (!this.nodeList.contains(currentNode)) {
            this.nodeList.add(currentNode);
        }

        sb.append("<" + qName + ">");

        this.level++;

    }

    /**
     * 当sax解释器读到了文本内容的时候会调用该方法 ch: 读取到的字符存储到了这个字符数组中。 start :从字符串数组中的指定索引值开始存储内容。 length : 存储了多少个字符到字符数组中。 注意的细节：
     * 1.如果标签体文本有很多的文本数据，那么这时候有可能调用一次characters方法无法读取完毕。这时候就会调用多次的characters方法。 2.如果标签体内容出现了实体字符，那么这时候也会分多次读取。
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//            sb.append(new String(ch, start, length));
    }

    /**
     * 当sax解释器读到结束标签的时候会调用该方法。 localName : 结束标签名字
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (sb.toString().endsWith("<" + qName + ">")) {
            this.level--;
            sb = sb.replace(sb.lastIndexOf("<" + qName + ">"), sb.length(), "");
        }
    }

}