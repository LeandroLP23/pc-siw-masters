package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

	@GetMapping("/cart")
	public String getCart(Model model) {
		return "cart";
	}
	
	@GetMapping("/allproducts")
	public String getAllProducts(Model model) {
		return "allproducts";
	}
	
	@GetMapping("/productpage")
	public String getProductPage(Model model) {
		return "productpage";
	}
	
	@GetMapping("/index")
	public String getIdex(Model model) {
		return "index";
	}
}
