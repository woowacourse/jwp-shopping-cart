package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    public String admin(Model model) {
        List<ProductResponse> productsResponse = cartService.readAll();
        model.addAttribute("products", productsResponse);
        return "admin";
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> create(@RequestBody ProductRequest productRequest) {
        cartService.create(productRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/update")
    public ResponseEntity<String> update(@RequestBody ProductUpdateRequest productUpdateRequest) {
        cartService.update(productUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
