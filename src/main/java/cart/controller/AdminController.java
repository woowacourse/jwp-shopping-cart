package cart.controller;

import cart.controller.dto.ProductCreateRequest;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String admin() {
        return "admin";
    }

    @PostMapping
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.create(request);
    }

}
