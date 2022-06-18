package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Accessory;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.controller.validator.AccessoryValidator;
import it.uniroma3.siw.service.AccessoryService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

public class AccessoryController {
    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private AccessoryValidator accessoryValidator;

    @GetMapping("/admin//accessory")
    public String addAccessory(Model model) {
        model.addAttribute("accessory", new Accessory());
        return "addAccessory";
    }

    @GetMapping("/accessory/{id}")
    public String getAccessory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("accessory", this.accessoryService.findById(id));
        return "accessory";
    }

    @GetMapping("/accessory")
    public String getAccessory(Model model) {
        model.addAttribute("accessory", this.accessoryService.findAll());
        return "accessoryList";
    }

    @PostMapping("/admin//accessory")
    public String addAccessory(@ModelAttribute("accessory") Accessory accessory,
                          Model model, BindingResult bindingResult) {
        this.accessoryValidator.validate(accessory, bindingResult);
        if (!bindingResult.hasErrors()) {
            this.accessoryService.save(accessory);
            model.addAttribute("accessory", this.accessoryService.findById(accessory.getId()));
            return "accessory";
        }
        return "addAccessory";
    }
}