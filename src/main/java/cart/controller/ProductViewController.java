package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.service.product.ProductService;

@Controller
public class ProductViewController {

	private final ProductService productService;

	public ProductViewController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public String displayHome(Model model) {
		model.addAttribute("products", productService.findAll());
		return "index";
	}

	@GetMapping("/admin")
	public String displayAdmin(Model model) {
		model.addAttribute("products", productService.findAll());
		return "admin";
	}

}
