package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private  RoleService roleService;
    private  UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public String pageForAdmin(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.listUser());
        model.addAttribute("userRole", roleService.getAllRoles());
        model.addAttribute("user", user);
        return "newAdm";
    }

    @PostMapping
    public String pageCreate(@ModelAttribute("user") User user,
                             @RequestParam(value = "createRoles", required = false) String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.passwordCoder(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String pageDelete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String pageEdit(@ModelAttribute("user") User user, @RequestParam(value = "editRoles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.passwordCoder(user);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
