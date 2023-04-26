package cart.controller;


import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String showProductsList(Model model) {
        model.addAttribute("products", cartService.findProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("products", cartService.findProducts());
        return "admin";
    }


    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        cartService.addProduct(productRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> products = cartService.findProducts();

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/product")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequestDto productRequestDto) {
        cartService.updateProduct(productRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        cartService.deleteProduct(id);

        return ResponseEntity.ok().build();
    }
}
