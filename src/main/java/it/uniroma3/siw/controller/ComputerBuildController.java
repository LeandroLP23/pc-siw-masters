package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ComputerBuildValidator;
import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.ComputerBuild;
import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.category.HardwareCategory;
import it.uniroma3.siw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ComputerBuildController {
    @Autowired
    private ComputerBuildService computerBuildService;

    @Autowired
    private ComputerBuildValidator computerBuildValidator;

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private ComputerCaseService computerCaseService;

    @Autowired
    private AccessoryService accessoryService;

    @GetMapping("/show/pageComputerBuild/{id}")
    public String getPageComputerBuild(Model model, @PathVariable("id") Long id) {

        model.addAttribute("computerBuild", this.computerBuildService.findById(id));

        return "pageComputerBuild";
    }

    @GetMapping("/admin/addComputerBuild")
    public String getAddComputerBuild(Model model) {

        model.addAttribute("computerBuild", new ComputerBuild());
        model.addAttribute("accessorySelected", new ArrayList<>());

        getAccessoryAndComputerCaseAndHardwareLists(model);

        return "admin/addComputerBuild";
    }

    @PostMapping("/admin/updateComputerBuild")
    public String addComputerBuild(@ModelAttribute("computerBuild") ComputerBuild computerBuild,
                                   @RequestParam(value = "idAccessory",required = false) List<Long> idAccessory,
                                   @RequestParam(value = "idComputerCase",required = false) Long idComputerCase,
                                   @RequestParam(value = "idCpu",required = false) Long idCpu,
                                   @RequestParam(value = "idGpu",required = false) Long idGpu,
                                   @RequestParam(value = "idMotherboard",required = false) Long idMotherboard,
                                   @RequestParam(value = "idRam",required = false) Long idRam,
                                   @RequestParam(value = "idStorage",required = false) Long idStorage,
                                   @RequestParam(value = "idPowerSupply",required = false) Long idPowerSupply,
                                   Model model, BindingResult bindingResult) {

        //Gli accessory non sono necessari, non controllo la loro presenza per chiamare un errore

        if(idComputerCase == null){
            bindingResult.reject("computerBuild.computerCase");
        }

        if(idCpu == null){
            bindingResult.reject("computerBuild.cpu");
        }

        if(idGpu == null){
            bindingResult.reject("computerBuild.gpu");
        }

        if(idMotherboard == null){
            bindingResult.reject("computerBuild.motherboard");
        }

        if(idRam == null){
            bindingResult.reject("computerBuild.ram");
        }

        if(idStorage == null){
            bindingResult.reject("computerBuild.storage");
        }

        if(idPowerSupply == null){
            bindingResult.reject("computerBuild.powerSupply");
        }

        this.computerBuildValidator.validate(computerBuild, bindingResult);

        if (!bindingResult.hasErrors()) {

            List<Accessory> accessoryList = getSelectedAccessories(idAccessory);

            computerBuild.setAccessoryList(accessoryList);

            computerBuild.setComputerCase(this.computerCaseService.findById(idComputerCase));

            List<Hardware> hardwareList = new ArrayList<>();

            hardwareList.add(this.hardwareService.findById(idCpu));
            hardwareList.add(this.hardwareService.findById(idGpu));
            hardwareList.add(this.hardwareService.findById(idMotherboard));
            hardwareList.add(this.hardwareService.findById(idRam));
            hardwareList.add(this.hardwareService.findById(idStorage));
            hardwareList.add(this.hardwareService.findById(idPowerSupply));

            computerBuild.setHardwareList(hardwareList);

            this.computerBuildService.save(computerBuild);

            model.addAttribute("computerBuild", this.computerBuildService.findById(computerBuild.getId()));

            return "pageComputerBuild";
        }else{

            model.addAttribute("computerBuild", computerBuild);

            getAccessoryAndComputerCaseAndHardwareLists(model);

            if(idComputerCase != null) {
                model.addAttribute("computerCaseSelected", this.computerCaseService.findById(idComputerCase));
            }

            if(idAccessory!=null){
                model.addAttribute("accessorySelected", getSelectedAccessories(idAccessory));
            }else{
                model.addAttribute("accessorySelected", new ArrayList<>());
            }

            if(idCpu != null){
                model.addAttribute("cpuSelected", this.hardwareService.findById(idCpu));
            }

            if(idGpu != null){
                model.addAttribute("gpuSelected", this.hardwareService.findById(idGpu));
            }

            if(idMotherboard != null){
                model.addAttribute("motherboardSelected", this.hardwareService.findById(idMotherboard));
            }

            if(idRam != null){
                model.addAttribute("ramSelected", this.hardwareService.findById(idRam));
            }

            if(idStorage != null){
                model.addAttribute("storageSelected", this.hardwareService.findById(idStorage));
            }

            if(idPowerSupply != null){
                model.addAttribute("powerSupplySelected", this.hardwareService.findById(idPowerSupply));
            }

            return "admin/editComputerBuild";
        }
    }

    @GetMapping("/admin/editComputerBuild/{id}")
    public String getEditComputerBuild(@PathVariable("id") Long id, Model model){
        ComputerBuild computerBuild = this.computerBuildService.findById(id);

        model.addAttribute("computerBuild", computerBuild);
        model.addAttribute("accessorySelected", computerBuild.getAccessoryList());

        getAccessoryAndComputerCaseAndHardwareLists(model);

        Map<HardwareCategory,Hardware> hardwareMap = computerBuild.getMappedHardware();

        for(HardwareCategory category: hardwareMap.keySet()){
            model.addAttribute(category.toString()+"Selected",hardwareMap.get(category));
        }

        model.addAttribute("computerCaseSelected",computerBuild.getComputerCase()  );
        return "admin/editComputerBuild";
    }

    @PostMapping("/admin/updateComputerBuild/{id}")
    public String updateComputerBuild(@ModelAttribute("computerBuild") ComputerBuild computerBuild,
                                   @RequestParam(value = "idAccessory",required = false) List<Long> idAccessory,
                                   @RequestParam(value = "idComputerCase",required = false) Long idComputerCase,
                                   @RequestParam(value = "idCpu",required = false) Long idCpu,
                                   @RequestParam(value = "idGpu",required = false) Long idGpu,
                                   @RequestParam(value = "idMotherboard",required = false) Long idMotherboard,
                                   @RequestParam(value = "idRam",required = false) Long idRam,
                                   @RequestParam(value = "idStorage",required = false) Long idStorage,
                                   @RequestParam(value = "idPowerSupply",required = false) Long idPowerSupply,
                                   Model model, BindingResult bindingResult) {

        //Gli accessory non sono necessari, non controllo la loro presenza per chiamare un errore

        if(idComputerCase == null){
            bindingResult.reject("computerBuild.computerCase");
        }

        if(idCpu == null){
            bindingResult.reject("computerBuild.cpu");
        }

        if(idGpu == null){
            bindingResult.reject("computerBuild.gpu");
        }

        if(idMotherboard == null){
            bindingResult.reject("computerBuild.motherboard");
        }

        if(idRam == null){
            bindingResult.reject("computerBuild.ram");
        }

        if(idStorage == null){
            bindingResult.reject("computerBuild.storage");
        }

        if(idPowerSupply == null){
            bindingResult.reject("computerBuild.powerSupply");
        }

        this.computerBuildValidator.validate(computerBuild, bindingResult);

        if (!bindingResult.hasErrors()) {

            List<Accessory> accessoryList = getSelectedAccessories(idAccessory);

            computerBuild.setAccessoryList(accessoryList);

            computerBuild.setComputerCase(this.computerCaseService.findById(idComputerCase));

            List<Hardware> hardwareList = new ArrayList<>();

            hardwareList.add(this.hardwareService.findById(idCpu));
            hardwareList.add(this.hardwareService.findById(idGpu));
            hardwareList.add(this.hardwareService.findById(idMotherboard));
            hardwareList.add(this.hardwareService.findById(idRam));
            hardwareList.add(this.hardwareService.findById(idStorage));
            hardwareList.add(this.hardwareService.findById(idPowerSupply));

            computerBuild.setHardwareList(hardwareList);

            this.computerBuildService.save(computerBuild);

            model.addAttribute("computerBuild", this.computerBuildService.findById(computerBuild.getId()));

            return "pageComputerBuild";
        }else{

            model.addAttribute("computerBuild", computerBuild);

            getAccessoryAndComputerCaseAndHardwareLists(model);

            if(idComputerCase != null) {
                model.addAttribute("computerCaseSelected", this.computerCaseService.findById(idComputerCase));
            }

            if(idAccessory!=null){
                model.addAttribute("accessorySelected", getSelectedAccessories(idAccessory));
            }else{
                model.addAttribute("accessorySelected", new ArrayList<>());
            }

            if(idCpu != null){
                model.addAttribute("cpuSelected", this.hardwareService.findById(idCpu));
            }

            if(idGpu != null){
                model.addAttribute("gpuSelected", this.hardwareService.findById(idGpu));
            }

            if(idMotherboard != null){
                model.addAttribute("motherboardSelected", this.hardwareService.findById(idMotherboard));
            }

            if(idRam != null){
                model.addAttribute("ramSelected", this.hardwareService.findById(idRam));
            }

            if(idStorage != null){
                model.addAttribute("storageSelected", this.hardwareService.findById(idStorage));
            }

            if(idPowerSupply != null){
                model.addAttribute("powerSupplySelected", this.hardwareService.findById(idPowerSupply));
            }

            return "admin/addComputerBuild";
        }
    }

    private void getAccessoryAndComputerCaseAndHardwareLists(Model model) {
        model.addAttribute("accessoryList", this.accessoryService.findAll());
        model.addAttribute("computerCaseList", this.computerCaseService.findAll());

        Map<HardwareCategory, List<Hardware>> hardwareMap = this.hardwareService.findOnCategory();
        model.addAttribute("cpuList", hardwareMap.get(HardwareCategory.cpu));
        model.addAttribute("gpuList", hardwareMap.get(HardwareCategory.gpu));
        model.addAttribute("motherboardList", hardwareMap.get(HardwareCategory.motherboard));
        model.addAttribute("ramList", hardwareMap.get(HardwareCategory.ram));
        model.addAttribute("storageList", hardwareMap.get(HardwareCategory.storage));
        model.addAttribute("powerSupplyList", hardwareMap.get(HardwareCategory.powerSupply));
    }

    private List<Accessory> getSelectedAccessories(List<Long> idAccessory) {
        if(idAccessory!=null){
            List<Accessory> accessoryList = new ArrayList<>();
            for(Long acID : idAccessory){
                accessoryList.add(this.accessoryService.findById(acID));
            }
            return accessoryList;
        }
        return new ArrayList<>();
    }

    @GetMapping("/show/pageAllComputerBuild")
    public String getPageAllComputerBuild(Model model){

        model.addAttribute("computerBuildList",this.computerBuildService.findAll());

        return "pageAllProducts";
    }
}