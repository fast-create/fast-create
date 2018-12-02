package com.alibaba.fastcreate.bs;

import com.alibaba.fastcreate.adapter.IExcelAdapter;
import com.alibaba.fastcreate.adapter.impl.ClazzModelAdapter;
import com.alibaba.fastcreate.model.ClazzModel;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bs")
@Log4j2
public class CreateController {

    @Autowired
    private IExcelAdapter excelAdapter;
    @Autowired
    private ClazzModelAdapter clazzModelAdapter;

    @PostMapping("/create")
    public Object create(@RequestParam String inputPath, @RequestParam String out) {
        log.info("== bs.create, inputPath is {}, out is {}", inputPath, out);
        try {
            List<ClazzModel> clazzModelList = excelAdapter.createExcelOutString(inputPath);
            clazzModelList.forEach(clazzModel -> {
                try {
                    clazzModelAdapter.createFile(clazzModel, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
