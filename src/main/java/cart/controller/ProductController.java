package cart.controller;

import cart.controller.response.ProductResponse;
import cart.service.GeneralProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
	private final GeneralProductService generalProductService;

	public ProductController(GeneralProductService generalProductService) {
		this.generalProductService = generalProductService;
	}

	@GetMapping("/")
	public String products(Model model) {
		final List<ProductResponse> findAllProducts = generalProductService.findAll();
		model.addAttribute("products", findAllProducts);
		return "index";
	}

	@GetMapping("/admin")
	public String showAdmin(Model model) {
		final List<ProductResponse> findAllProducts = generalProductService.findAll();
		model.addAttribute("products", findAllProducts);
		return "admin";
	}
}
