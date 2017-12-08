package com.hanyang.instateam.web.controller;

import com.hanyang.instateam.model.Collaborator;
import com.hanyang.instateam.model.Role;
import com.hanyang.instateam.service.CollaboratorService;
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
public class CollaboratorController {
  @Autowired
  private CollaboratorService collaboratorService;

  @Autowired
  private RoleService roleService;

  @RequestMapping("/collaborators")
  public String listCollaborators(Model model) {
    model.addAttribute("collaborator", new Collaborator());
    model.addAttribute("collaborators", collaboratorService.findAll());
    model.addAttribute("roles", roleService.findAll());
    return "collaborators";
  }

  @RequestMapping(value = "/update-collaborator-role", method = RequestMethod.POST)
  public String updateCollaborator(@RequestParam(value = "collaboratorRole", required = false) List<String> ids) {
    System.out.println(ids == null ? "null" : "Not Null");
    for (int i = 0; i < ids.size(); i++) {
      String[] idStr = ids.get(i).split(",");
      Collaborator collaborator = collaboratorService.findById(Long.valueOf(idStr[0]));
      Role role = roleService.findById(Long.valueOf(idStr[1]));
      collaborator.setRole(role);
      collaboratorService.save(collaborator);
    }

    return "redirect:/collaborators";
  }

  @RequestMapping(value = "/addcollaborator", method = RequestMethod.POST)
  public String addCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes){
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);
      redirectAttributes.addFlashAttribute("collaborator", collaborator);

      return "redirect:/collaborators";
    }

    collaboratorService.save(collaborator);
    return "redirect:/collaborators";
  }

  @RequestMapping("/formEditCollaborator/{collaboratorId}")
  public String formEditCollaborator(@PathVariable Long collaboratorId, Model model) {
    Collaborator collaborator = collaboratorService.findById(collaboratorId);
    List<Role> roles = roleService.findAll();

    model.addAttribute("collaborator", collaborator);
    model.addAttribute("roles", roles);

    return "edit_collaborator";
  }

  @RequestMapping(value = "editCollaborator/{collaboratorId}")
  public String editCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);
      redirectAttributes.addFlashAttribute("collaborator", collaborator);

      return String.format("redirect:/formEditCollaborator/%s", collaborator.getId());
    }

    collaboratorService.save(collaborator);
    return "redirect:/collaborators";
  }
}
