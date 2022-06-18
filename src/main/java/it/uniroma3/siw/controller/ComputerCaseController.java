package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ComputerCaseValidator;
import it.uniroma3.siw.controller.validator.NotebookValidator;
import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.service.ComputerCaseService;
import it.uniroma3.siw.service.NotebookService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ComputerCaseController {

    @Autowired
    private ComputerCaseService computerCaseService;

    @Autowired
    private ComputerCaseValidator computerCaseValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/pageComputerCase/{id}")
    public String getPageComputerCase(Model model, @PathVariable("id") Long id){

        model.addAttribute("computerCase",this.computerCaseService.findById(id));

        return "pageComputerCase";
    }

    @GetMapping("/admin/addComputerCase")
    public String getAddComputerCase(Model model){

        model.addAttribute("computerCase", new ComputerCase());
        model.addAttribute("vendorList", this.vendorService.findAll());

        return "admin/addComputerCase";
    }

}
