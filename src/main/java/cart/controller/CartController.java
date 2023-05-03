package cart.controller;

import cart.auth.Auth;
import cart.dto.CartRequestDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final String REDIRECT_URL = "/";

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Auth final Integer userId,
                                       @RequestBody @Valid final CartRequestDto cartRequestDto) {
        cartService.create(cartRequestDto, userId);
        return ResponseEntity.created(URI.create(REDIRECT_URL)).build();
    }
}
