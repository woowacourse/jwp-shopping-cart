package cart.controller;

import cart.dto.entity.MemberCartEntity;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartsRestController {
    private CartService cartService;

    public CartsRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<MemberCartEntity>> getCarts() {
        List<MemberCartEntity> carts = cartService.findAll();

        return ResponseEntity.ok(carts);
    }
}
