package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.category.AccessoryCategory;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.controller.validator.AccessoryValidator;
import it.uniroma3.siw.service.AccessoryService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccessoryController {
    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private AccessoryValidator accessoryValidator;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/show/pageAccessory/{id}")
    public String getPageAccessory(Model model, @PathVariable("id") Long id) {

        model.addAttribute("accessory", this.accessoryService.findById(id));

        return "pageAccessory";
    }

    @GetMapping("/admin/addAccessory")
    public String getAddAccessory(Model model) {

        model.addAttribute("accessory", new Accessory());
        model.addAttribute("vendorList", this.vendorService.findAll());

        return "admin/addAccessory";
    }

    /*@GetMapping("/accessory")
    public String getAccessory(Model model) {
        model.addAttribute("accessory", this.accessoryService.findAll());
        return "accessoryList";
    }*/

    @PostMapping("/admin/pageAccessory")
    public String addAccessory(@ModelAttribute("accessory") Accessory accessory,
                               @RequestParam(value = "category",required = false) AccessoryCategory category,
                               @RequestParam(value = "idVendor",required = false) Long idVendor,
                               Model model, BindingResult bindingResult) {

        if(category ==  null){
            bindingResult.reject("accessory.category");
        }

        if(idVendor == 0){
            bindingResult.reject("accessory.vendor");
        }

        this.accessoryValidator.validate(accessory, bindingResult);

        if (!bindingResult.hasErrors()) {

            accessory.setCategory(category);

            accessory.setVendor(this.vendorService.findById(idVendor));

            this.accessoryService.save(accessory);

            model.addAttribute("accessory", this.accessoryService.findById(accessory.getId()));

            return "pageAccessory";
        }else{

            model.addAttribute("accessory", accessory);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if(idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            if(category!=null){
                model.addAttribute("categorySelected", category);
            }

            return "admin/addAccessory";
        }
    }

    @PostMapping("/admin/updateAccessory/{id}")
    public String updateAccessory(@PathVariable Long id, @ModelAttribute("accessory") Accessory accessory,
                                @RequestParam(value = "category",required = false) AccessoryCategory category,
                                @RequestParam(value = "idVendor",required = false) Long idVendor,
                                Model model, BindingResult bindingResult) {

        if (category == null) {
            bindingResult.reject("accessory.category");
        }

        if (idVendor == 0) {
            bindingResult.reject("accessory.vendor");
        }

        this.accessoryValidator.validate(accessory, bindingResult);

        if (!bindingResult.hasErrors()) {

            accessory.setCategory(category);

            accessory.setVendor(this.vendorService.findById(idVendor));

            this.accessoryService.save(accessory);

            model.addAttribute("accessory", this.accessoryService.findById(accessory.getId()));

            return "pageAccessory";
        } else {

            model.addAttribute("accessory", accessory);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if (idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            if (category != null) {
                model.addAttribute("categorySelected", category);
            }

            return "admin/editAccessory";
        }
    }

    @GetMapping("/admin/editAccessory/{id}")
    public String editAccessory(@PathVariable("id") Long id, Model model) {
        Accessory accessory = accessoryService.findById(id);
        model.addAttribute("accessory", accessory);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("categorySelected", accessory.getCategory());
        model.addAttribute("vendorSelected", accessory.getVendor());

        return "admin/editAccessory";
    }

    @GetMapping("/admin/deleteAccessory/{id}")
    public String deleteAccessory(@PathVariable("id") Long id, Model model) {
        this.accessoryService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/show/pageAllAccessory")
    public String getPageAllAccessory(Model model){

        model.addAttribute("accessoryList",this.accessoryService.findAll());

        return "pageAllProducts";
    }
}