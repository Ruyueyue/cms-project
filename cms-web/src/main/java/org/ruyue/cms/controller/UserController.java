package org.ruyue.cms.controller;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Param;
import org.ruyue.cms.dao.IUserDao;
import org.ruyue.cms.dto.UserDto;
import org.ruyue.cms.model.User;
import org.ruyue.cms.service.IGroupService;
import org.ruyue.cms.service.IRoleService;
import org.ruyue.cms.service.IUserService;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    private IUserService userService;
    private IGroupService groupService;
    private IRoleService roleService;
    private IUserDao userDao;
    private SessionFactory sessionFactory;


    public IGroupService getGroupService() {
        return groupService;
    }
    @Inject
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    public IRoleService getRoleService() {
        return roleService;
    }
    @Inject
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public IUserService getUserService() {
        return userService;
    }

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/users")
    public String list(Model model) {
        model.addAttribute("datas",userService.findUser());
        return "user/list";
    }

    private void initAddUser(Model model) {
        model.addAttribute("roles",roleService.listRole());
        model.addAttribute("groups", groupService.listGroup());
    }

    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("userDto",new UserDto());//user,user
        initAddUser(model);
        return "user/add";
    }

    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@Valid UserDto userDto, BindingResult br, Model model) {
        if(br.hasErrors()) {
            initAddUser(model);
            return "user/add";
        }
        userService.add(userDto.getUser(), userDto.getRoleIds(), userDto.getGroupIds());
        return "redirect:/admin/user/users";
    }

    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable int id,Model model) {
        User u = userService.load(id);
        model.addAttribute("userDto",new UserDto(u,
                userService.listUserRoleIds(id),
                userService.listUserGroupIds(id)));//user,user
        initAddUser(model);
        return "user/update";
    }

    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(@PathVariable int id,@Valid UserDto userDto,BindingResult br,Model model) {
        if(br.hasErrors()) {
            System.out.println(br.hasErrors());
            initAddUser(model);
            return "user/update";
        }
        User ou = userService.load(id);
        ou.setNickname(userDto.getNickname());
        ou.setPhone(userDto.getPhone());
        ou.setEmail(userDto.getEmail());
        ou.setStatus(userDto.getStatus());
        userService.update(ou, userDto.getRoleIds(), userDto.getGroupIds());
        return "redirect:/admin/user/users";
    }

    @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
    public String delete(@PathVariable int id) {
        userService.delete(id);

        return "redirect:/admin/user/users";
    }

    @RequestMapping(value="/updateStatus/{id}",method=RequestMethod.GET)
    public String updateStatus(@PathVariable int id) {
        User u=userService.load(id);
        System.out.println(u.getStatus()+u.getUsername());
        userService.updataStatus(id);
        System.out.println(u.getStatus());

        return "redirect:/admin/user/users";
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String show(@PathVariable int id,Model model){
        model.addAttribute(userService.load(id));
        model.addAttribute("gs",userService.listUserGroups(id));
        model.addAttribute("rs",userService.listUserRoles(id));
        return "user/show";
    }




}
