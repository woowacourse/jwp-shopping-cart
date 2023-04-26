package cart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.CartService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    public AdminController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody final ProductRequest productRequest) {
        long id = cartService.create(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @GetMapping
    public String getProductList(final Model model) {
        List<ProductResponse> products = cartService.read();
        model.addAttribute("products", products);
        return "admin";
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody final ProductRequest productRequest) {
        cartService.update(id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
