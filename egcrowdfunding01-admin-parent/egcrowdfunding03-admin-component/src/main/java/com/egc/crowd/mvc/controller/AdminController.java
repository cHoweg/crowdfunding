package com.egc.crowd.mvc.controller;

import com.egc.crowd.bean.Admin;
import com.egc.crowd.constant.CrowdConstant;
import com.egc.crowd.mapper.AdminMapper;
import com.egc.crowd.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/28 上午9:58
 */

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/update.html")
    public String update(Admin admin,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("keyword") String keyword) {

        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword") String keyword,
                             ModelMap modelMap) {

        // 1.根据Id查询Admin对象
        Admin admin = adminService.getAdminById(adminId);

        // 2.将Admin对象存入模型
        modelMap.addAttribute("admin", admin);

        return "admin-edit";
    }

    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {

        adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html?&pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) {

        // 执行删除
        adminService.remove(adminId);

        // 页面跳转，回到分页页面
        // return "forward:/admin/get/page.html";  转发刷新会重复执行删除
        return "redirect:/admin/get/page.html?&pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {

        // 强制session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session) {

        // 调用Service方法执行登录检查
        // 能返回admin对象说明登录成功，如果账户密码不正确则抛异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的admin对象存入Session
        session.setAttribute(CrowdConstant.ATT_NAME_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(

            // 使用@RequestParam注解的defaultValue属性，指定默认值
            // keyword默认空字符串，和Sql语句配合使用
            @RequestParam(value = "keyword", defaultValue = "") String keyword,

            // pageNum默认值1
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,

            // pageSize默认5
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,

            ModelMap modelMap
    ) {

        // 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将PageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATT_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

}
