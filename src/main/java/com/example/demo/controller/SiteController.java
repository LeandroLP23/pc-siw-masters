package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

	@GetMapping("/cart")
	public String getCart(Model model) {
		return "cart.html";
	}
	
	@GetMapping("/allproducts")
	public String getAllProducts(Model model) {
		return "allproducts.html";
	}
	
	@GetMapping("/index")
	public String getIdex(Model model) {
		return "index.html";
	}
}
