package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/create")
    public String create(@RequestBody @Valid ProductRequest productRequest) {
        productService.create(productRequest);
        return "admin";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PutMapping("/admin/{productId}")
    public String update(@PathVariable Long productId,
                         @RequestBody @Valid ProductRequest productRequest) {
        productService.update(productId, productRequest);
        return "admin";
    }

    @DeleteMapping("/admin/{productId}")
    public String delete(@PathVariable Long productId) {
        productService.deleteById(productId);
        return "admin";
    }
}
