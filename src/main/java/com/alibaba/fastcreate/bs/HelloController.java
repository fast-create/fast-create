package com.alibaba.fastcreate.bs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

}
