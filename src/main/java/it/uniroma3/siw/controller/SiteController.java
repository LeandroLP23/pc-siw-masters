package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AccessoryService;
import it.uniroma3.siw.service.ComputerBuildService;
import it.uniroma3.siw.service.HardwareService;
import it.uniroma3.siw.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

	@Autowired
	private AccessoryService accessoryService;

	@Autowired
	private NotebookService notebookService;

	@Autowired
	private HardwareService hardwareService;

	@Autowired
	private ComputerBuildService computerBuildService;
	@GetMapping(path = {"", "/index","/"})
	public String getIndex(Model model) {

		model.addAttribute("randomAccessory", this.accessoryService.findRandom());
		model.addAttribute("randomHardware", this.hardwareService.findRandom());
		model.addAttribute("randomComputerBuild", this.computerBuildService.findRandom());
		model.addAttribute("randomNotebook", this.notebookService.findRandom());

		return "index";
	}
}
