package com.alibaba.fastcreate.bs;

import static org.junit.Assert.*;

import com.alibaba.fastcreate.BaseBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateControllerTest extends BaseBootTest {

    @Autowired
    private CreateController createController;

    @Test
    public void create() {
        // given
        String inputPath = "/Users/mengjunya/Desktop/TestModel.xlsx";
        String out = "/Users/mengjunya/Desktop";

        // when

        Object result = createController.create(inputPath, out);

        // then
    }
}