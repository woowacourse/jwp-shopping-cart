package cart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String admin(final Model model) {
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/product")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductRequest productRequest) {
        final Long id = productService.save(productRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        productService.update(id, productRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
