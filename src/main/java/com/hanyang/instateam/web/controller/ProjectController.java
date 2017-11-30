package com.hanyang.instateam.web.controller;

import com.hanyang.instateam.model.Collaborator;
import com.hanyang.instateam.model.Project;
import com.hanyang.instateam.model.Role;
import com.hanyang.instateam.service.ProjectService;
import com.hanyang.instateam.service.RoleService;
import java.util.ArrayList;
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
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @Autowired
  private RoleService roleService;

  // index page
  @RequestMapping("/index")
  public String lsitProjects(Model model) {
    List<Project> projects = projectService.findAll();

    model.addAttribute("projects", projects);
    return "index";
  }

  @RequestMapping("/")
  public String home() {
    return "redirect:/index";
  }

  @RequestMapping("/addproject")
  public String formAddProject(Model model) {
    if (!model.containsAttribute("project")) {
      model.addAttribute("project", new Project());
    }

    model.addAttribute("action", "/index");
    model.addAttribute("submit", "Add");
    model.addAttribute("roles", roleService.findAll());

    return "edit_project";
  }

  @RequestMapping("/edit/{projectId}")
  public String formEditProject(@PathVariable Long projectId, Model model) {
    Project project = projectService.findById(projectId);

    model.addAttribute("project", project);
    model.addAttribute("action", String.format("/project/%s", projectId));
    model.addAttribute("submit", "Save");
    model.addAttribute("roles", roleService.findAll());

    return "edit_project";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes, @RequestParam("project_roles") List<Long> ids) {
    if (result.hasErrors()) {
      System.out.println("error");
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

      redirectAttributes.addFlashAttribute("project", project);

      return "redirect:/addproject";
    }

    //System.out.println("success");
    List<Role> roles = new ArrayList<>();
    for (Long id : ids) {
      roles.add(roleService.findById(id));
    }
    project.setRolesNeeded(roles);

    projectService.save(project);

    return "redirect:/index";
  }

  @RequestMapping(value = "/project/{projectId}", method = RequestMethod.POST)
  public String editProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

      redirectAttributes.addFlashAttribute("project", project);

      return String.format("redirect:/edit/%s", project.getId());
    }

    projectService.save(project);

    return String.format("redirect:/detail/%s", project.getId());
  }

  @RequestMapping("/detail/{projectId}")
  public String projectDetail(@PathVariable Long projectId, Model model) {
    Project project = projectService.findById(projectId);
    List<Role> roles = project.getRolesNeeded();
    List<Collaborator> collaborators = project.getCollaborators();

    model.addAttribute("project", project);
    model.addAttribute("roles", roles);
    model.addAttribute("collaborators", collaborators);
    return "project_detail";
  }
}
