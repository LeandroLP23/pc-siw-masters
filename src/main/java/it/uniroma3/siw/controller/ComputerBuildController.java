package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ComputerBuildValidator;
import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.ComputerBuild;
import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.category.HardwareCategory;
import it.uniroma3.siw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private static final String pictureFolder = "/images/computerBuild/";

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

    @PostMapping("/admin/pageComputerBuild")
    public String addComputerBuild(@ModelAttribute("computerBuild") ComputerBuild computerBuild,
                                   @RequestParam(value = "idAccessory",required = false) List<Long> idAccessory,
                                   @RequestParam(value = "idComputerCase",required = false) Long idComputerCase,
                                   @RequestParam(value = "idCpu",required = false) Long idCpu,
                                   @RequestParam(value = "idGpu",required = false) Long idGpu,
                                   @RequestParam(value = "idMotherboard",required = false) Long idMotherboard,
                                   @RequestParam(value = "idRam",required = false) Long idRam,
                                   @RequestParam(value = "idStorage",required = false) Long idStorage,
                                   @RequestParam(value = "idPowerSupply",required = false) Long idPowerSupply,
                                   @RequestParam("file") MultipartFile image,
                                   Model model, BindingResult bindingResult) {

        //Gli accessory non sono necessari, non controllo la loro presenza per chiamare un errore

        paramValidator(computerBuild, idComputerCase, idCpu, idGpu, idMotherboard, idRam, idStorage, idPowerSupply, image,bindingResult, false);

        if (!bindingResult.hasErrors()) {

            settingRequestedParam(computerBuild, idAccessory, idComputerCase, idCpu, idGpu, idMotherboard, idRam, idStorage, idPowerSupply);

            //Set picture
            computerBuild.setPicture(MainController.SavePicture(pictureFolder,image));

            this.computerBuildService.save(computerBuild);

            model.addAttribute("computerBuild", this.computerBuildService.findById(computerBuild.getId()));

            return "pageComputerBuild";
        }else{

            //Fill editPage with pre-filled parameters
            fillEditPageParam(idAccessory, idComputerCase, idCpu, idGpu, idMotherboard, idRam, idStorage, idPowerSupply, model);
            model.addAttribute("mode","edit");

            return "admin/addComputerBuild";
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
                                      @RequestParam("file") MultipartFile image,
                                      @PathVariable("id") Long id,
                                      Model model, BindingResult bindingResult) {

        //Gli accessory non sono necessari, non controllo la loro presenza per chiamare un errore

        paramValidator(computerBuild, idComputerCase, idCpu, idGpu, idMotherboard, idRam, idStorage, idPowerSupply,image, bindingResult, true);

        if (!bindingResult.hasErrors()) {

            //Setting accessoryList
            settingRequestedParam(computerBuild, idAccessory, idComputerCase, idCpu, idGpu, idMotherboard, idRam, idStorage, idPowerSupply);

            //Update and Set picture
            if(!image.isEmpty())
            {
                ComputerBuild previousBuild = this.computerBuildService.findById(id);
                String fileName = previousBuild.getPicture().replace(pictureFolder, "");
                if(fileName.contains("default.png"))
                {
                    computerBuild.setPicture(MainController.SavePicture(pictureFolder,image));
                }
                else {
                    computerBuild.setPicture(MainController.SavePicture(fileName, pictureFolder, image));
                }
            }
            else
            {
                computerBuild.setPicture(this.computerBuildService.findById(id).getPicture());
            }

            //Save
            computerBuild.setId(id);
            this.computerBuildService.save(computerBuild);

            model.addAttribute("computerBuild", this.computerBuildService.findById(computerBuild.getId()));

            return "pageComputerBuild";
        }else{

            fillEditPageParam(idAccessory, idComputerCase, idCpu, idGpu, idMotherboard, idRam, idStorage, idPowerSupply, model);

            return "admin/editComputerBuild";
        }
    }

    @GetMapping("/admin/deleteComputerBuild/{id}")
    public String deleteComputerBuild(@PathVariable("id") Long id, Model model) {
        this.computerBuildService.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/show/pageAllComputerBuild")
    public String getPageAllComputerBuild(Model model){

        model.addAttribute("computerBuildList",this.computerBuildService.findAll());

        return "pageAllProducts";
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

    private void paramValidator(@ModelAttribute("computerBuild") ComputerBuild computerBuild,
                                @RequestParam(value = "idComputerCase", required = false) Long idComputerCase,
                                @RequestParam(value = "idCpu", required = false) Long idCpu,
                                @RequestParam(value = "idGpu", required = false) Long idGpu,
                                @RequestParam(value = "idMotherboard", required = false) Long idMotherboard,
                                @RequestParam(value = "idRam", required = false) Long idRam,
                                @RequestParam(value = "idStorage", required = false) Long idStorage,
                                @RequestParam(value = "idPowerSupply", required = false) Long idPowerSupply,
                                @RequestParam("file") MultipartFile image,
                                BindingResult bindingResult,
                                Boolean isUpdating) {
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
        if(isUpdating)
        {
            this.computerBuildValidator.validateUpdate(computerBuild,image,bindingResult);
        }
        else
        {
            this.computerBuildValidator.validate(computerBuild, bindingResult);
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

    private void settingRequestedParam(ComputerBuild computerBuild, List<Long> idAccessory, Long idComputerCase, Long idCpu, Long idGpu, Long idMotherboard, Long idRam, Long idStorage, Long idPowerSupply) {

        //Setting accessoryList
        List<Accessory> accessoryList = getSelectedAccessories(idAccessory);
        computerBuild.setAccessoryList(accessoryList);

        //Setting computerCase
        ComputerCase computerCase =this.computerCaseService.findById(idComputerCase);
        computerBuild.setComputerCase(computerCase);

        //Setting hardwareList
        List<Hardware> hardwareList = new ArrayList<>();
        hardwareList.add(this.hardwareService.findById(idCpu));
        hardwareList.add(this.hardwareService.findById(idGpu));
        hardwareList.add(this.hardwareService.findById(idMotherboard));
        hardwareList.add(this.hardwareService.findById(idRam));
        hardwareList.add(this.hardwareService.findById(idStorage));
        hardwareList.add(this.hardwareService.findById(idPowerSupply));
        computerBuild.setHardwareList(hardwareList);

        //Getting total price
        Float priceComputerBuild = (float) 0;
        for(Accessory accessory: accessoryList){
            priceComputerBuild+=accessory.getPrice();
        }
        for(Hardware hardware: hardwareList){
            priceComputerBuild+=hardware.getPrice();
        }
        priceComputerBuild+=computerCase.getPrice();
        computerBuild.setPrice(priceComputerBuild);

    }

    private void fillEditPageParam(@RequestParam(value = "idAccessory", required = false) List<Long> idAccessory, @RequestParam(value = "idComputerCase", required = false) Long idComputerCase, @RequestParam(value = "idCpu", required = false) Long idCpu, @RequestParam(value = "idGpu", required = false) Long idGpu, @RequestParam(value = "idMotherboard", required = false) Long idMotherboard, @RequestParam(value = "idRam", required = false) Long idRam, @RequestParam(value = "idStorage", required = false) Long idStorage, @RequestParam(value = "idPowerSupply", required = false) Long idPowerSupply, Model model) {
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
    }

}