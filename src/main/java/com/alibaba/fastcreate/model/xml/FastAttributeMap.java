package com.alibaba.fastcreate.model.xml;

import java.util.Map;
import lombok.Data;

@Data
public class FastAttributeMap {

    private FastNode namedItem;

    private Map<String, Object> attributeMap;

}
