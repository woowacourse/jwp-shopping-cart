package cart.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.service.response.ProductResponse;
import cart.service.ProductService;
import cart.service.ProductServiceImpl;

@Controller
public class ProductViewController {
	private final ProductService productService;

	public ProductViewController(ProductServiceImpl productService) {
		this.productService = productService;
	}

	@GetMapping("/")
	public String products(Model model) {
		final List<ProductResponse> findAllProducts = productService.findAll();
		model.addAttribute("products", findAllProducts);
		return "index";
	}

	@GetMapping("/admin")
	public String showAdmin(Model model) {
		final List<ProductResponse> findAllProducts = productService.findAll();
		model.addAttribute("products", findAllProducts);
		return "admin";
	}
}
