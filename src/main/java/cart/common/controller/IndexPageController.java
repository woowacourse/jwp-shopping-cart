package cart.common.controller;

import cart.product.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    private final ProductRepository productRepository;

    public IndexPageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String productList(final Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }
}
