package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.VendorValidator;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorValidator vendorValidator;

    @GetMapping("/admin/addVendor")
    public String getAddVendor(Model model){

        model.addAttribute("vendor", new Vendor());

        return "admin/addVendor";
    }

    //TODO POST-MAPPING su pagina con tutti i venditori
    @PostMapping("/admin/pageAllVendor")
    public String addVendor(@ModelAttribute("vendor")Vendor vendor, BindingResult bindingResult, Model model){

        this.vendorValidator.validate(vendor,bindingResult);

        if(!bindingResult.hasErrors()){

            this.vendorService.save(vendor);

            //Torna sulla index
            return "redirect:/index";
        }else{
            return "admin/addVendor";
        }
    }
}
