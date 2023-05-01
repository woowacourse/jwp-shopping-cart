package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.service.product.ProductService;
import cart.service.user.UserService;

@Controller
public class ViewController {

	private final ProductService productService;
	private final UserService userService;

	public ViewController(final ProductService productService, final UserService userService) {
		this.productService = productService;
		this.userService = userService;
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

	@GetMapping("/settings")
	public String displaySettings(final Model model) {
		model.addAttribute("members", userService.findAll());

		return "settings";
	}

	@GetMapping("/cart")
	public String displayCart(final Model model) {
		// model.addAttribute("cartItem", cartService.findAll());

		return "cart";
	}

}
