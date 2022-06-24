package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.AccessoryValidator;
import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.category.AccessoryCategory;
import it.uniroma3.siw.service.AccessoryService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccessoryController {
    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private AccessoryValidator accessoryValidator;

    @Autowired
    private VendorService vendorService;

    private static final String pictureFolder = "/images/accessory/";

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

    @PostMapping("/admin/pageAccessory")
    public String addAccessory(@ModelAttribute("accessory") Accessory accessory,
                               @RequestParam(value = "category",required = false) AccessoryCategory category,
                               @RequestParam(value = "idVendor",required = false) Long idVendor,
                               @RequestParam("file") MultipartFile image,
                               Model model, BindingResult bindingResult) {

        paramValidator(accessory, category, idVendor, image, bindingResult, false);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            accessory.setCategory(category);
            accessory.setVendor(this.vendorService.findById(idVendor));
            accessory.setPicture(MainController.SavePicture(pictureFolder+category.name()+"/", image));
            //Save
            this.accessoryService.save(accessory);

            model.addAttribute("accessory", this.accessoryService.findById(accessory.getId()));
            return "pageAccessory";
        }else{

            //Fill editPage with pre-filled parameters
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


    @GetMapping("/admin/editAccessory/{id}")
    public String getEditAccessory(@PathVariable("id") Long id, Model model) {

        Accessory accessory = accessoryService.findById(id);

        model.addAttribute("accessory", accessory);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("categorySelected", accessory.getCategory());
        model.addAttribute("vendorSelected", accessory.getVendor());

        return "admin/editAccessory";
    }

    @PostMapping("/admin/updateAccessory/{id}")
    public String updateAccessory(@PathVariable Long id, @ModelAttribute("accessory") Accessory accessory,
                                  @RequestParam(value = "category",required = false) AccessoryCategory category,
                                  @RequestParam(value = "idVendor",required = false) Long idVendor,
                                  @RequestParam("file") MultipartFile image,
                                  Model model, BindingResult bindingResult) {

        paramValidator(accessory, category, idVendor, image, bindingResult, true);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            accessory.setCategory(category);
            accessory.setVendor(this.vendorService.findById(idVendor));

            //Update picture
            if(!image.isEmpty())
            {
                Accessory previousAccessory = this.accessoryService.findById(id);
                String fileName = previousAccessory.getPicture().replace(pictureFolder+category.name()+"/", "");
                if(fileName.contains("default.png"))
                {
                    accessory.setPicture(MainController.SavePicture(pictureFolder+category.name()+"/",image));
                }
                else {
                    accessory.setPicture(MainController.SavePicture(fileName, pictureFolder + category.name() + "/", image));
                }
            }
            else
            {
                accessory.setPicture(this.accessoryService.findById(id).getPicture());
            }

            //Save
            accessory.setId(id);
            this.accessoryService.save(accessory);

            model.addAttribute("accessory", this.accessoryService.findById(accessory.getId()));

            return "pageAccessory";
        } else {

            //Fill editPage with pre-filled parameters
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


    @GetMapping("/admin/deleteAccessory/{id}")
    public String deleteAccessory(@PathVariable("id") Long id, Model model) {

        this.accessoryService.deleteById(id);

        return "redirect:/index";
    }

    @GetMapping("/show/pageAllAccessory")
    public String getPageAllAccessory(Model model){

        model.addAttribute("accessoryList",this.accessoryService.findAll());

        return "pageAllProducts";
    }


    private void paramValidator(@ModelAttribute("accessory") Accessory accessory,
                                @RequestParam(value = "category", required = false) AccessoryCategory category,
                                @RequestParam(value = "idVendor", required = false) Long idVendor,
                                @RequestParam("file") MultipartFile image,
                                BindingResult bindingResult,
                                Boolean isUpdating) {
        if(category ==  null){
            bindingResult.reject("accessory.category");
        }

        if(idVendor == 0){
            bindingResult.reject("accessory.vendor");
        }

        if(accessory.getPrice() == null){
            bindingResult.reject("accessory.price");
        }

        if(isUpdating)
        {
            this.accessoryValidator.validateUpdate(accessory,image,bindingResult);
        }
        else
        {
            this.accessoryValidator.validate(accessory,bindingResult);
        }
    }
}