package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.HardwareValidator;
import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.category.HardwareCategory;
import it.uniroma3.siw.service.HardwareService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HardwareController {

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private HardwareValidator hardwareValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/pageHardware/{id}")
    public String getPageHardware(Model model, @PathVariable("id") Long id){

        model.addAttribute("hardware",this.hardwareService.findById(id));

        return "pageHardware";
    }

    @GetMapping("/admin/addHardware")
    public String getAddHardware(Model model){

        model.addAttribute("hardware", new Hardware());
        model.addAttribute("vendors", this.vendorService.findAll());
        model.addAttribute("categories", HardwareCategory.values());

        return "admin/addHardware";
    }
}
