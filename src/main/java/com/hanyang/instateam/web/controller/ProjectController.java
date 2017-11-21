package com.hanyang.instateam.web.controller;

import com.hanyang.instateam.model.Project;
import com.hanyang.instateam.service.ProjectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  // index page
  @RequestMapping("/")
  public String lsitProjects(Model model) {
    List<Project> projects = projectService.findAll();

    model.addAttribute("projects", projects);
    return "index";
  }
}
