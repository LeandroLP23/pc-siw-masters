package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.NotebookValidator;
import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.service.NotebookService;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NotebookValidator notebookValidator;

    @Autowired
    private VendorService vendorService;

    private static final String pictureFolder = "/images/notebook/";

    @GetMapping("/show/pageNotebook/{id}")
    public String getPageHardware(Model model, @PathVariable("id") Long id) {

        model.addAttribute("notebook", this.notebookService.findById(id));

        return "pageNotebook";
    }

    @GetMapping("/admin/addNotebook")
    public String getAddNotebook(Model model) {

        model.addAttribute("notebook", new Notebook());
        model.addAttribute("vendorList", this.vendorService.findAll());

        return "admin/addNotebook";
    }


    @PostMapping("/admin/pageNotebook")
    public String addNotebook(@ModelAttribute("notebook") Notebook notebook,
                              @RequestParam(value = "idVendor", required = false) Long idVendor,
                              @RequestParam("file") MultipartFile image,
                              Model model, BindingResult bindingResult) {

        validate(notebook, idVendor, image,bindingResult, false);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            notebook.setVendor(this.vendorService.findById(idVendor));
            notebook.setPicture(MainController.SavePicture(pictureFolder,image));

            //Save
            this.notebookService.save(notebook);

            model.addAttribute("notebook", this.notebookService.findById(notebook.getId()));

            return "pageNotebook";
        } else {

            //Fill editPage with pre-filled parameters
            model.addAttribute("vendorList", this.vendorService.findAll());

            if (idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            return "admin/addNotebook";
        }
    }

    private void validate(@ModelAttribute("notebook") Notebook notebook,
                          @RequestParam(value = "idVendor", required = false) Long idVendor,
                          @RequestParam("file") MultipartFile image,
                          BindingResult bindingResult, boolean isUpdating) {
        if (idVendor == 0)
            bindingResult.reject("notebook.vendor");

        if(notebook.getPrice() == null){
            bindingResult.reject("notebook.price");
        }
        if(isUpdating)
            this.notebookValidator.validateUpdate(notebook,image ,bindingResult);
        else
        {
            this.notebookValidator.validate(notebook, bindingResult);
        }
    }

    @GetMapping("/admin/editNotebook/{id}")
    public String getEditNotebook(@PathVariable("id") Long id, Model model) {
        Notebook notebook = notebookService.findById(id);

        model.addAttribute("notebook", notebook);
        model.addAttribute("vendorList", vendorService.findAll());
        model.addAttribute("vendorSelected", notebook.getVendor());

        return "admin/editNotebook";
    }

    @PostMapping("/admin/updateNotebook/{id}")
    public String editNotebook(@PathVariable Long id, @ModelAttribute("notebook") Notebook notebook,
                               @RequestParam(value = "idVendor", required = false) Long idVendor,
                               @RequestParam("file") MultipartFile image,
                               Model model, BindingResult bindingResult) {

        validate(notebook, idVendor, image, bindingResult, true);

        if (!bindingResult.hasErrors()) {

            //Setting Requested Parameters
            notebook.setVendor(this.vendorService.findById(idVendor));

            //Update and Set picture
            if(!image.isEmpty())
            {
                Notebook previousNotebook = this.notebookService.findById(id);
                String fileName = previousNotebook.getPicture().replace(pictureFolder, "");
                if(fileName.contains("default.png"))
                {
                    notebook.setPicture(MainController.SavePicture(pictureFolder,image));
                }
                else {
                    notebook.setPicture(MainController.SavePicture(fileName, pictureFolder, image));
                }
            }
            else
            {
                notebook.setPicture(this.notebookService.findById(id).getPicture());
            }

            //Save
            notebook.setId(id);
            this.notebookService.save(notebook);

            model.addAttribute("notebook", this.notebookService.findById(notebook.getId()));

            return "pageNotebook";
        } else {

            //Fill editPage with pre-filled parameters
            model.addAttribute("notebook", notebook);
            model.addAttribute("vendorList", this.vendorService.findAll());

            if (idVendor != 0) {
                model.addAttribute("vendorSelected", this.vendorService.findById(idVendor));
            }

            return "admin/editNotebook";
        }
    }

    @GetMapping("/admin/deleteNotebook/{id}")
    public String deleteNotebook(@PathVariable("id") Long id, Model model) {

        this.notebookService.deleteById(id);

        return "redirect:/index";
    }

    @GetMapping("/show/pageAllNotebook")
    public String getPageAllNotebook(Model model){

        model.addAttribute("notebookList",this.notebookService.findAll());

        return "pageAllProducts";
    }
}
