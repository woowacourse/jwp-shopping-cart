package cart.controller;

import cart.controller.dto.ProductDto;
import cart.domain.ProductCategory;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(final Model model) {
        final List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @ModelAttribute("categories")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
