package com.hanyang.instateam.web.controller;

import com.hanyang.instateam.model.Role;
import com.hanyang.instateam.service.RoleService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoleController {
  @Autowired
  private RoleService roleService;

  @RequestMapping("/roles")
  public String listAllRoles(Model model) {
    List<Role> roles = roleService.findAll();

    model.addAttribute("role", new Role());
    model.addAttribute("roles", roles);
    return "roles";
  }

  @RequestMapping("editrole/{roleId}")
  public String formEditRole(@PathVariable Long roleId, Model model) {
    Role role = roleService.findById(roleId);
    model.addAttribute("role", role);
    return "edit_role";
  }

  @RequestMapping(value = "/roles", method = RequestMethod.POST)
  public String addRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
    //System.out.println(role.getName());
    if (result.hasErrors()) {
      //System.out.println("enter");
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
      redirectAttributes.addFlashAttribute("role", role);

      return "redirect:/roles";
    }

    roleService.save(role);
    return "redirect:/roles";
  }

  @RequestMapping(value = "/editrole/{roleId}", method = RequestMethod.POST)
  public String editRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
      redirectAttributes.addFlashAttribute("role", role);

      return String.format("redirect:/editrole/%s", role.getId());
    }

    roleService.save(role);
    return "redirect:/roles";
  }
}
