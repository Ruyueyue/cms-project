package org.ruyue.cms.controller;


import org.ruyue.cms.dao.IGroupDao;
import org.ruyue.cms.model.Group;
import org.ruyue.cms.service.IGroupService;
import org.ruyue.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @program: cms-parent
 * @description: 组管理
 * @author: Ruyue Bian
 * @create: 2019-05-26 22:04
 */
@RequestMapping("/admin/group")
@Controller
public class GroupController {
    private IGroupService groupService;
    private IUserService userService;


    public IUserService getUserService() {
        return userService;
    }

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public IGroupService getGroupService() {
        return groupService;
    }

    @Inject
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping("/groups")
    public String list(Model model) {
        model.addAttribute("datas",groupService.findGroup());
        return "group/list";
    }

    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Group());
        return "group/add";
    }

    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@Validated Group group,BindingResult br) {
        if(br.hasErrors()) {
            return "group/add";
        }
        groupService.add(group);
        return "redirect:/admin/group/groups";
    }

    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable int id,Model model) {
        model.addAttribute(groupService.load(id));
        return "group/update";
    }

    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(@PathVariable int id,@Validated Group group,BindingResult br) {
        if(br.hasErrors()) {
            return "group/update";
        }
        Group ug = groupService.load(id);
        System.out.println(ug.getDescr());
        ug.setDescr(group.getDescr());
        ug.setName(group.getName());
        groupService.update(ug);
        System.out.println(ug.getDescr());
        return "redirect:/admin/group/groups";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        groupService.delete(id);
        return "redirect:/admin/group/groups";
    }

    @RequestMapping("/{id}")
    public String show(@PathVariable int id,Model model) {
        model.addAttribute(groupService.load(id));
        model.addAttribute("us", userService.listGroupUsers(id));
        return "group/show";
    }

    @RequestMapping("/clearUsers/{id}")
    public String clearGroupUsers(@PathVariable int id) {
        groupService.deleteGroupUsers(id);
        return "redirect:/admin/group/groups";
    }
}
