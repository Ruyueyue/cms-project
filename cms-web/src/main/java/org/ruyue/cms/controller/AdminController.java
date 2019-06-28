package org.ruyue.cms.controller;

import org.ruyue.basic.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: cms-parent
 * @description: 控制器
 * @author: Ruyue Bian
 * @create: 2019-05-22 22:49
 */
@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String index(){
        return "admin/index";
    }
}
