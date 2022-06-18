package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.AccessoryValidator;
import it.uniroma3.siw.controller.validator.ComputerBuildValidator;
import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.ComputerBuild;
import it.uniroma3.siw.model.category.AccessoryCategory;
import it.uniroma3.siw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ComputerBuildController {
    @Autowired
    private ComputerBuildService computerBuildService;

    @Autowired
    private ComputerBuildValidator computerBuildValidator;

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private ComputerCaseService computerCaseService;

    @Autowired
    private AccessoryService accessoryService;

    @GetMapping("/pageComputerBuild/{id}")
    public String getPageComputerBuild(Model model, @PathVariable("id") Long id) {

        model.addAttribute("computerBuild", this.computerBuildService.findById(id));

        return "pageComputerBuild";
    }

    @GetMapping("/admin/addComputerBuild")
    public String getAddComputerBuild(Model model) {

        model.addAttribute("computerBuild", new ComputerBuild());
        model.addAttribute("accessoryList", this.accessoryService.findAll());
        model.addAttribute("computerCaseList", this.computerCaseService.findAll());
        model.addAttribute("hardwareList", this.hardwareService.findAll());

        return "admin/addComputerBuild";
    }

}