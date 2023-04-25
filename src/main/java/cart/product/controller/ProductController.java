package cart.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import cart.product.dto.ProductRequest;
import cart.product.service.ProductService;

@Controller
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/")
	public String showProducts(Model model) {
		model.addAttribute("products", productService.findAll());
		return "index";
	}

	@GetMapping("/admin")
	public String showAdmin(Model model) {
		model.addAttribute("products", productService.findAll());
		return "admin";
	}

	@PostMapping("/products")
	public String createProducts(ProductRequest productRequest) {
		productService.saveProduct(productRequest);
		return "redirect:/";
	}
}
