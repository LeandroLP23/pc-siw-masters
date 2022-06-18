package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.NotebookValidator;
import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.service.NotebookService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NotebookValidator notebookValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/pageNotebook/{id}")
    public String getPageHardware(Model model, @PathVariable("id") Long id){

        model.addAttribute("notebook",this.notebookService.findById(id));

        return "pageNotebook";
    }

    @GetMapping("/admin/addNotebook")
    public String getAddNotebook(Model model){

        model.addAttribute("notebook", new Notebook());
        model.addAttribute("vendors", this.vendorService.findAll());

        return "admin/addNotebook";
    }

}
