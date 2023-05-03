package cart.controller;

import cart.auth.Login;
import cart.domain.member.Member;
import cart.service.CartService;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(
            @Login Member member,
            @PathVariable @NotNull Long productId
    ) {
        cartService.addProduct(member, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
