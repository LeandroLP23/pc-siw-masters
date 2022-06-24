package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.VendorValidator;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.service.AccessoryService;
import it.uniroma3.siw.service.ComputerCaseService;
import it.uniroma3.siw.service.HardwareService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorValidator vendorValidator;

    @Autowired
    private ComputerCaseService computerCaseService;

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private AccessoryService accessoryService;

    private static final String pictureFolder = "/images/vendor/";

    @GetMapping("/admin/addVendor")
    public String getAddVendor(Model model) {

        model.addAttribute("vendor", new Vendor());

        return "admin/addVendor";
    }

    @PostMapping("/admin/pageAllVendor")
    public String addVendor(@ModelAttribute("vendor") Vendor vendor,
                            @RequestParam("file") MultipartFile image,
                            BindingResult bindingResult, Model model) {

        this.vendorValidator.validate(vendor, bindingResult);

        if (!bindingResult.hasErrors()) {
            vendor.setPicture(MainController.SavePicture(pictureFolder,image));
            this.vendorService.save(vendor);

            return "redirect:/index";
        } else {
            return "admin/addVendor";
        }
    }

    @GetMapping("/admin/editVendor/{id}")
    public String getEditAccessory(@PathVariable("id") Long id, Model model) {

        Vendor vendor = vendorService.findById(id);

        model.addAttribute("vendor", vendor);

        return "admin/editVendor";
    }

    @PostMapping("/admin/updateVendor/{id}")
    public String editVendor(@PathVariable Long id,
                             @ModelAttribute("vendor") Vendor vendor,
                             @RequestParam("file") MultipartFile image,
                             BindingResult bindingResult, Model model) {

        this.vendorValidator.validateUpdate(vendor, image, bindingResult);

        if (!bindingResult.hasErrors()) {

            //Update and Set picture
            if(!image.isEmpty())
            {
                Vendor previousVendor = this.vendorService.findById(id);
                String fileName = previousVendor.getPicture().replace(pictureFolder, "");
                if(fileName.contains("default.png"))
                {
                    vendor.setPicture(MainController.SavePicture(pictureFolder,image));
                }
                else {
                    vendor.setPicture(MainController.SavePicture(fileName, pictureFolder, image));
                }
            }
            else
            {
                vendor.setPicture(this.vendorService.findById(id).getPicture());
            }

            //Save
            vendor.setId(id);
            this.vendorService.save(vendor);

            return "redirect:/index";
        } else {
            return "admin/editVendor";
        }
    }

    @GetMapping("/admin/deleteVendor/{id}")
    public String deleteVendor(@PathVariable("id") Long id, Model model) {
        this.vendorService.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/show/pageAllVendor")
    public String getPageAllVendor(Model model){

        model.addAttribute("vendorList",this.vendorService.findAll());

        return "pageAllProducts";
    }

    @GetMapping("/show/pageAllVendorItems/{id}")
    public String getPageAllVendorItems(@PathVariable("id") Long id, Model model){

        Vendor vendor = this.vendorService.findById(id);

        model.addAttribute("vendorHardwareList",this.hardwareService.findByVendor(vendor));
        model.addAttribute("vendorAccessoryList",this.accessoryService.findByVendor(vendor));
        model.addAttribute("vendorComputerCaseList",this.computerCaseService.findByVendor(vendor));

        return "pageAllProducts";
    }

    @GetMapping("/show/pageAllItems")
    public String getPageAllItems(Model model){

        model.addAttribute("allHardwareList",this.hardwareService.findAll());
        model.addAttribute("allAccessoryList",this.accessoryService.findAll());
        model.addAttribute("allComputerCaseList",this.computerCaseService.findAll());

        return "pageAllProducts";
    }
}
