package com.dli.controllers;

import com.dli.entities.Demo;
import com.dli.helper.OfficeUtil;
import com.dli.services.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.nativejdbc.OracleJdbc4NativeJdbcExtractor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DemoService demoService;

    @Value("${html.path}")
    private String htmlPath;

    @RequestMapping("")
    public String index() {
        logger.info("smile backend service is running...");
        return "smile backend service is running...";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map add(@RequestBody Map body) {
        System.out.print(body);
        return body;
    }

    @RequestMapping(value = "/getDemoList", method = RequestMethod.GET)
    public Map getDemoList() {
        List<Demo> demoList = demoService.getDemoList();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "successful");
        result.put("result", demoList);
        return result;
    }

    @RequestMapping(value = "/getDemo", method = RequestMethod.GET)
    public Map getDemo(@RequestParam int id) {
        Demo demo = demoService.getDemoById(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "successful");
        result.put("result", demo);
        return result;
    }


    @RequestMapping(value = "/addDemo", method = RequestMethod.PUT)
    public Map addDemo(@RequestBody Map body) {
        String fullName = (String) body.get("full_name");
        Demo demo = new Demo();
        demo.setFullName(fullName);
        Map<String, Object> result = new HashMap<String, Object>();
        int row = demoService.insertDemo(demo);
        result.put("status", "successful");
        result.put("result", row + " row affected.");
        return result;
    }

    @RequestMapping(value = "/updateDemo", method = RequestMethod.PATCH)
    public Map updateDemo(@RequestBody Map body) {
        int id = (Integer) body.get("id");
        String fullName = (String) body.get("full_name");
        Demo demo = new Demo();
        demo.setId(id);
        demo.setFullName(fullName);
        Map<String, Object> result = new HashMap<String, Object>();
        int row = demoService.updateDemo(demo);
        result.put("status", "successful");
        result.put("result", row + " row affected.");
        return result;
    }


    @RequestMapping(value = "/deleteDemo", method = RequestMethod.POST)
    public Map deleteDemo(@RequestBody Map body) {
        int id = (Integer) body.get("id");
        Map<String, Object> result = new HashMap<String, Object>();
        int row = demoService.deleteDemo(id);
        result.put("status", "successful");
        result.put("result", row + " row affected.");
        return result;
    }

    @RequestMapping(value = "/officeUtilTest", method = RequestMethod.GET)
    public String officeUtilTest() {
        try {
            //OfficeUtil.getInstance().word2html("/home/wangqc/Desktop/德银网点运营排队机分析技术方案1.03.doc",htmlPath);
            //OfficeUtil.getInstance().word2html("/home/wangqc/Desktop/德银网点运营排队机分析技术方案1.03.docx",htmlPath);
            //OfficeUtil.getInstance().ppt2html("/home/wangqc/Desktop/ETL调度设计.ppt",htmlPath);
            //OfficeUtil.getInstance().ppt2html("/root/smile/files/course/office/ETL调度设计.pptx",htmlPath);

            /*String sheetName = "testSheet";
            List<String> rowNameList = new ArrayList<>();
            rowNameList.add("序号");
            rowNameList.add("姓名");
            rowNameList.add("性别");
            rowNameList.add("出生日期");

            List<Object[]> dataList = new ArrayList<>();
            Object[] dataArray = new Object[4];
            dataArray[0] = 1;
            dataArray[1] = "张三";
            dataArray[2] = "男";
            dataArray[3] = "1984-01-01 12:12:12";
            dataList.add(dataArray);

            OfficeUtil.getInstance().export2excel(sheetName, rowNameList, dataList,"/home/wangqc/Desktop/testExcel.xlsx");*/

            List<Object[]> result = OfficeUtil.getInstance().extractExcel("/home/wangqc/Desktop/合计.xlsx");
            for(Object[] objectArray : result){
                for(Object obj: objectArray){
                    logger.info(obj.toString());
                }
            }




        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "finished";
    }


}
