package com.alibaba.fastcreate.bs;

import com.alibaba.fastcreate.BaseBootTest;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateControllerTest extends BaseBootTest {

    @Autowired
    private CreateController createController;

    @Test
    public void createXLSX() {
        // given
        String inputPath = "/Users/mengjunya/Desktop/TestModel.xlsx";
        String out = "/Users/mengjunya/Desktop/TestModel";

        // when
        Object result = createController.create(inputPath, out);

        // then
        Assert.assertTrue(Objects.equals(result, Boolean.TRUE));
    }

    @Test
    public void createXML() {
        // given
        String inputPath = "/Users/mengjunya/Desktop/TestModel.xml";
        String out = "/Users/mengjunya/Desktop/TestModel";

        // when
        Object result = createController.create(inputPath, out);

        // then
        Assert.assertTrue(Objects.equals(result, Boolean.TRUE));
    }

}