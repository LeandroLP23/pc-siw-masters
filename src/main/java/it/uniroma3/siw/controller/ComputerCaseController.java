package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ComputerCaseValidator;
import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.service.ComputerCaseService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ComputerCaseController {

    @Autowired
    private ComputerCaseService computerCaseService;

    @Autowired
    private ComputerCaseValidator computerCaseValidator;

    @Autowired
    private VendorService vendorService;

    private static final String pictureFolder = "/images/computerCase/";

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
                                   @RequestParam("file") MultipartFile image,
                                   Model model, BindingResult bindingResult ) {

        validate(computerCase, idVendor, image, bindingResult, false);

        if (!bindingResult.hasErrors()) {

            computerCase.setVendor(this.vendorService.findById(idVendor));
            computerCase.setPicture(MainController.SavePicture(pictureFolder,image));

            this.computerCaseService.save(computerCase);

            model.addAttribute("computerCase", this.computerCaseService.findById(computerCase.getId()));

            return "pageComputerCase";
        } else {

            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            model.addAttribute("mode","edit");

            return "admin/addComputerCase";
        }
    }

    @GetMapping("/admin/editComputerCase/{id}")
    public String getEditComputerCase(@PathVariable("id") Long id, Model model) {

        ComputerCase computerCase = computerCaseService.findById(id);

        model.addAttribute("computerCase", computerCase);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("vendorSelected", computerCase.getVendor());

        return "admin/editComputerCase";
    }

    @PostMapping("/admin/updateComputerCase/{id}")
    public String updateComputerCase(@PathVariable Long id, @ModelAttribute("computerCase") ComputerCase computerCase,
                                     @RequestParam(value = "idVendor",required = false) Long idVendor,
                                     @RequestParam("file") MultipartFile image,
                                     Model model, BindingResult bindingResult ) {

        validate(computerCase, idVendor, image, bindingResult, true);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            computerCase.setVendor(this.vendorService.findById(idVendor));

            //Update and Set picture
            if(!image.isEmpty())
            {
                ComputerCase previousCase = this.computerCaseService.findById(id);
                String fileName = previousCase.getPicture().replace(pictureFolder, "");
                if(fileName.contains("default.png"))
                {
                    computerCase.setPicture(MainController.SavePicture(pictureFolder,image));
                }
                else {
                    computerCase.setPicture(MainController.SavePicture(fileName, pictureFolder, image));
                }
            }
            else
            {
                computerCase.setPicture(this.computerCaseService.findById(id).getPicture());
            }

            //Save
            computerCase.setId(id);
            this.computerCaseService.save(computerCase);

            model.addAttribute("computerCase", this.computerCaseService.findById(computerCase.getId()));

            return "pageComputerCase";
        } else {

            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0)
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));

            return "admin/editComputerCase";
        }
    }

    @GetMapping("/admin/deleteComputerCase/{id}")
    public String deleteComputerCase(@PathVariable("id") Long id, Model model) {

        this.computerCaseService.deleteById(id);

        return "redirect:/index";
    }

    @GetMapping("/show/pageAllComputerCase")
    public String getPageAllComputerCase(Model model){

        model.addAttribute("computerCaseList",this.computerCaseService.findAll());

        return "pageAllProducts";
    }

    private void validate(@ModelAttribute("computerCase") ComputerCase computerCase,
                          @RequestParam(value = "idVendor",required = false) Long idVendor,
                          @RequestParam("file") MultipartFile image,
                          BindingResult bindingResult,
                          Boolean isUpdating) {
        if (idVendor == 0)
            bindingResult.reject("computerCase.vendor");

        if(computerCase.getPrice() == null){
            bindingResult.reject("computerCase.price");
        }
        if(isUpdating)
        {
            this.computerCaseValidator.validateUpdate(computerCase,image,bindingResult);
        }
        else
        {
            this.computerCaseValidator.validate(computerCase, bindingResult);
        }
    }
}
