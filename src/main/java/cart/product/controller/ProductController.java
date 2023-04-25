package cart.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
		productService.saveProducts(productRequest);
		return "admin";
	}

	@PutMapping("/products/{id}")
	public String updateProducts(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
		productService.updateProducts(id, productRequest);
		return "admin";
	}

	@DeleteMapping("/products/{id}")
	public String deleteProducts(@PathVariable Long id) {
		productService.deleteProductsById(id);
		return "admin";
	}

}
