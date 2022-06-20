package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

	
	@GetMapping("/allproducts")
	public String getAllProducts(Model model) {
		return "pageAllProducts";
	}
	
	@GetMapping("/productpage")
	public String getProductPage(Model model) {
		return "productpage";
	}
	
	@GetMapping(path = {"", "/index","/"})
	public String getIndex(Model model) {
		return "index";
	}
}
