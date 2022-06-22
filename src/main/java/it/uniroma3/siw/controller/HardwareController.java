package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.HardwareValidator;
import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.category.HardwareCategory;
import it.uniroma3.siw.service.HardwareService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HardwareController {

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private HardwareValidator hardwareValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/show/pageHardware/{id}")
    public String getPageHardware(Model model, @PathVariable("id") Long id){

        model.addAttribute("hardware",this.hardwareService.findById(id));

        return "pageHardware";
    }

    @GetMapping("/admin/addHardware")
    public String getAddHardware(Model model){

        model.addAttribute("hardware", new Hardware());
        model.addAttribute("vendorList", this.vendorService.findAll());

        return "admin/addHardware";
    }

    @PostMapping("/admin/pageHardware")
    public String addHardware(@ModelAttribute("hardware") Hardware hardware,
                              @RequestParam(value = "category",required = false) HardwareCategory category,
                              @RequestParam(value = "idVendor",required = false) Long idVendor,
                              Model model, BindingResult bindingResult) {

        paramValidator(hardware, category, idVendor, bindingResult);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            hardware.setCategory(category);
            hardware.setVendor(this.vendorService.findById(idVendor));

            //Save
            this.hardwareService.save(hardware);

            model.addAttribute("hardware", this.hardwareService.findById(hardware.getId()));
            return "pageHardware";
        }else{
            //Fill editPage with pre-filled parameters
            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            if(category!=null){
                model.addAttribute("categorySelected", category);
            }

            return "admin/addHardware";
        }
    }

    @GetMapping("/admin/editHardware/{id}")
    public String getEditHardware(@PathVariable("id") Long id, Model model) {

        Hardware hardware = hardwareService.findById(id);

        model.addAttribute("hardware", hardware);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("categorySelected", hardware.getCategory());
        model.addAttribute("vendorSelected", hardware.getVendor());

        return "admin/editHardware";
    }

    @PostMapping("/admin/updateHardware/{id}")
    public String updateHardware(@PathVariable Long id, @ModelAttribute("hardware") Hardware hardware,
                               @RequestParam(value = "category",required = false) HardwareCategory category,
                               @RequestParam(value = "idVendor",required = false) Long idVendor,
                               Model model, BindingResult bindingResult) {

        paramValidator(hardware, category, idVendor, bindingResult);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            hardware.setCategory(category);
            hardware.setVendor(this.vendorService.findById(idVendor));

            //Save
            hardware.setId(id);
            this.hardwareService.save(hardware);

            model.addAttribute("hardware", this.hardwareService.findById(hardware.getId()));
            return "pageHardware";
        }else{

            //Fill editPage with pre-filled parameters
            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            if(category!=null){
                model.addAttribute("categorySelected", category);
            }

            return "admin/editHardware";
        }
    }

    @GetMapping("/admin/deleteHardware/{id}")
    public String deleteHardware(@PathVariable("id") Long id, Model model) {
        this.hardwareService.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/show/pageAllHardware")
    public String getPageAllHardware(Model model){

        model.addAttribute("hardwareList",this.hardwareService.findAll());

        return "pageAllProducts";
    }

    private void paramValidator(@ModelAttribute("hardware") Hardware hardware,
                                @RequestParam(value = "category", required = false) HardwareCategory category,
                                @RequestParam(value = "idVendor", required = false) Long idVendor,
                                BindingResult bindingResult) {
        if(category ==  null){
            bindingResult.reject("hardware.category");
        }

        if(idVendor == 0){
            bindingResult.reject("hardware.vendor");
        }

        if(hardware.getPrice() == null){
            bindingResult.reject("hardware.price");
        }

        this.hardwareValidator.validate(hardware, bindingResult);
    }
}
