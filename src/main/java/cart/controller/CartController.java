package cart.controller;

import cart.dto.CreateProductRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/products")
    public String products() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> create(@RequestBody CreateProductRequest createProductRequest) {
        cartService.create(createProductRequest);
        return ResponseEntity.ok().build();
    }
}
