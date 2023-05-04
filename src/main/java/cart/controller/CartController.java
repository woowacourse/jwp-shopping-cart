package cart.controller;

import cart.controller.dto.CartRequest;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@RequestBody @Valid final CartRequest cartRequest) {
        cartService.saveCart(cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .build();
    }
}
