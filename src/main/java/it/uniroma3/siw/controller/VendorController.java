package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.VendorValidator;
import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.service.ComputerCaseService;
import it.uniroma3.siw.service.HardwareService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorValidator vendorValidator;

    @GetMapping("/admin/addVendor")
    public String getAddVendor(Model model) {

        model.addAttribute("vendor", new Vendor());

        return "admin/addVendor";
    }

    //TODO POST-MAPPING su pagina con tutti i venditori
    @PostMapping("/admin/pageAllVendor")
    public String addVendor(@ModelAttribute("vendor") Vendor vendor, BindingResult bindingResult, Model model) {

        this.vendorValidator.validate(vendor, bindingResult);

        if (!bindingResult.hasErrors()) {

            this.vendorService.save(vendor);

            //Torna sulla index
            return "redirect:/index";
        } else {
            return "admin/addVendor";
        }
    }

    @GetMapping("/admin/editVendor/{id}")
    public String editAccessory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("vendor", vendorService.findById(id));
        return "admin/editVendor";
    }

    @Transactional
    @PostMapping("/admin/updateVendor/{id}")
    public String editVendor(@PathVariable Long id, @ModelAttribute("vendor") Vendor vendor, BindingResult bindingResult, Model model) {
        this.vendorValidator.validate(vendor, bindingResult);

        if (!bindingResult.hasErrors()) {

            this.vendorService.save(vendor);

            //Torna sulla index
            return "redirect:/index";
        } else {
            return "admin/editVendor";
        }
    }

    @GetMapping("/admin/deleteVendor/{id}")
    public String deleteVendor(@PathVariable("id") Long id, Model model) {
        this.vendorService.deleteById(id);
        return "redirect:/";
    }
}
