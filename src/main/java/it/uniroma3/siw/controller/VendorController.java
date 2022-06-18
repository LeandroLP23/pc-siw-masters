package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.NotebookValidator;
import it.uniroma3.siw.controller.validator.VendorValidator;
import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.service.NotebookService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
