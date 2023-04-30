package cart.controller;

import cart.dto.MemberAuthDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/carts")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> putInCart(@RequestParam Long productId, MemberAuthDto memberAuthDto) {
        final Long savedCartId = cartService.putInCart(memberAuthDto, productId);
        return ResponseEntity.created(URI.create("/carts/" + savedCartId)).build();
    }
}
