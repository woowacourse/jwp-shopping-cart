package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.controller.response.ProductResponse;
import cart.service.ProductServiceImpl;

@Controller
public class ProductController {
	private final ProductServiceImpl productServiceImpl;

	public ProductController(ProductServiceImpl productServiceImpl) {
		this.productServiceImpl = productServiceImpl;
	}

	@GetMapping("/")
	public String products(Model model) {
		final List<ProductResponse> findAllProducts = productServiceImpl.findAll();
		model.addAttribute("products", findAllProducts);
		return "index";
	}

	@GetMapping("/admin")
	public String showAdmin(Model model) {
		final List<ProductResponse> findAllProducts = productServiceImpl.findAll();
		model.addAttribute("products", findAllProducts);
		return "admin";
	}
}
