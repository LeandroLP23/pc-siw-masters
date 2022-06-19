package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.NotebookValidator;
import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.model.category.AccessoryCategory;
import it.uniroma3.siw.service.NotebookService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NotebookValidator notebookValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/pageNotebook/{id}")
    public String getPageHardware(Model model, @PathVariable("id") Long id) {

        model.addAttribute("notebook", this.notebookService.findById(id));

        return "pageNotebook";
    }

    @GetMapping("/admin/addNotebook")
    public String getAddNotebook(Model model) {

        model.addAttribute("notebook", new Notebook());
        model.addAttribute("vendorList", this.vendorService.findAll());

        return "admin/addNotebook";
    }

    @PostMapping("/admin/pageNotebook")
    public String addNotebook(@ModelAttribute("notebook") Notebook notebook,
                              @RequestParam(value = "idVendor", required = false) Long idVendor,
                              Model model, BindingResult bindingResult) {

        if (idVendor == 0)
            bindingResult.reject("notebook.vendor");

        this.notebookValidator.validate(notebook, bindingResult);

        if (!bindingResult.hasErrors()) {

            notebook.setVendor(this.vendorService.findById(idVendor));

            this.notebookService.save(notebook);

            model.addAttribute("notebook", this.notebookService.findById(notebook.getId()));

            return "pageNotebook";
        } else {

            model.addAttribute("notebook", notebook);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if (idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            return "admin/addNotebook";
        }
    }

    @GetMapping("/admin/editNotebook/{id}")
    public String editNotebook(@PathVariable("id") Long id, Model model) {
        Notebook notebook = notebookService.findById(id);
        model.addAttribute("notebook", notebook);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("vendorSelected", notebook.getVendor());

        return "admin/editNotebook";
    }

    @Transactional
    @PostMapping("/admin/updateNotebook/{id}")
    public String editNotebook(@PathVariable Long id, @ModelAttribute("notebook") Notebook notebook,
                               @RequestParam(value = "idVendor", required = false) Long idVendor,
                               Model model, BindingResult bindingResult) {
        if (idVendor == 0)
            bindingResult.reject("notebook.vendor");

        this.notebookValidator.validate(notebook, bindingResult);

        if (!bindingResult.hasErrors()) {

            notebook.setVendor(this.vendorService.findById(idVendor));

            this.notebookService.save(notebook);

            model.addAttribute("notebook", this.notebookService.findById(notebook.getId()));

            return "pageNotebook";
        } else {

            model.addAttribute("notebook", notebook);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if (idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            return "admin/editNotebook";
        }
    }
}
