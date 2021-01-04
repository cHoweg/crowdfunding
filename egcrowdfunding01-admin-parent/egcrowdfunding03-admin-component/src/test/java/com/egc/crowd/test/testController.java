package com.egc.crowd.test;

import com.egc.crowd.bean.Admin;
import com.egc.crowd.bean.ParamData;
import com.egc.crowd.bean.Student;
import com.egc.crowd.service.AdminService;
import com.egc.crowd.util.CrowdUtil;
import com.egc.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/24 下午3:03
 */

@Controller
public class testController {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(testController.class);

    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student) {
        logger.info("Student: " + student.toString());
        return ResultEntity.successWithData(student);
    }

    @ResponseBody
    @RequestMapping("/send/arrayOne.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer number : array) {
            System.out.println("number= " + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/arrayTow.html")
    public String testReceiveArrayTow(ParamData paramData) {
        List<Integer> array = paramData.getArray();

        for (Integer number : array) {
            System.out.println("number= " + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/arrayThree.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {
        for (Integer number :
                array) {
            logger.info("number: " + number);
        }
        return "success";
    }

    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap, HttpServletRequest request) {
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult: " + judgeRequestType);

        List<Admin> adminList = adminService.getAll();

        modelMap.addAttribute("adminList", adminList);

         System.out.println(10/0);

        // String a = null;

        // System.out.println(a.length());

        return "target";
    }
}
