package cart.controller;

import cart.entity.Product;
import cart.service.ProductService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productList(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    //TODO: Entity가 아닌 DTO로 받도록 수정
    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        System.out.println("product = " + product);
        long id = productService.createProduct(product);
        return ResponseEntity.created(URI.create("/admin/" + id)).build();
    }
}
