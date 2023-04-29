package cart.controller;

import cart.dto.ResponseProductDto;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    private final ProductService productService;

    @Autowired
    public CartController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        final List<ResponseProductDto> responseProductDtos = productService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<ResponseProductDto> responseProductDtos = productService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }
}
