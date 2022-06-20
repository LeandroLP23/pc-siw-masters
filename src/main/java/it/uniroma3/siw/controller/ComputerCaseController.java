package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ComputerCaseValidator;
import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.service.ComputerCaseService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ComputerCaseController {

    @Autowired
    private ComputerCaseService computerCaseService;

    @Autowired
    private ComputerCaseValidator computerCaseValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/show/pageComputerCase/{id}")
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

    @PostMapping("/admin/pageComputerCase")
    public String addComputerCase (@ModelAttribute("computerCase") ComputerCase computerCase,
                               @RequestParam(value = "idVendor",required = false) Long idVendor,
                               Model model, BindingResult bindingResult ) {

        if (idVendor == 0)
            bindingResult.reject("computerCase.vendor");

        this.computerCaseValidator.validate(computerCase, bindingResult);

        if (!bindingResult.hasErrors()) {

            computerCase.setVendor(this.vendorService.findById(idVendor));

            this.computerCaseService.save(computerCase);

            model.addAttribute("computerCase", this.computerCaseService.findById(computerCase.getId()));

            return "pageComputerCase";
        } else {

            model.addAttribute("computerCase", computerCase);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            return "admin/addComputerCase";
        }
    }

    @PostMapping("/admin/updateComputerCase/{id}")
    public String editComputerCase(@PathVariable Long id, @ModelAttribute("computerCase") ComputerCase computerCase,
                                @RequestParam(value = "idVendor",required = false) Long idVendor,
                                Model model, BindingResult bindingResult ) {

        if (idVendor == 0)
            bindingResult.reject("computerCase.vendor");

        this.computerCaseValidator.validate(computerCase, bindingResult);

        if (!bindingResult.hasErrors()) {

            computerCase.setVendor(this.vendorService.findById(idVendor));

            this.computerCaseService.save(computerCase);

            model.addAttribute("computerCase", this.computerCaseService.findById(computerCase.getId()));

            return "pageComputerCase";
        } else {

            model.addAttribute("computerCase", computerCase);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            return "admin/editComputerCase";
        }
    }

    @GetMapping("/admin/editComputerCase/{id}")
    public String editComputerCase(@PathVariable("id") Long id, Model model) {
        ComputerCase computerCase = computerCaseService.findById(id);
        model.addAttribute("computerCase", computerCase);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("vendorSelected", computerCase.getVendor());

        return "admin/editComputerCase";
    }

    @GetMapping("/admin/deleteComputerCase/{id}")
    public String deleteComputerCase(@PathVariable("id") Long id, Model model) {
        this.computerCaseService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/show/pageAllComputerCase")
    public String getPageAllComputerCase(Model model){

        model.addAttribute("computerCaseList",this.computerCaseService.findAll());

        return "pageAllProducts";
    }
}
