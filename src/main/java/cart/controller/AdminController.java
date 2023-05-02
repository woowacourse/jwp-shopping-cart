package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String findAllProducts(final Model model) {
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);
        return "admin";
    }
}
