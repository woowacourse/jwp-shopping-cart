package cart.controller;

import cart.config.auth.LoginBasic;
import cart.domain.member.Member;
import cart.dto.product.ProductsResponseDto;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponseDto> findMemberCarts(@LoginBasic final Member member) {
        return ResponseEntity.ok(cartService.findAll(member));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable final Long productId,
                                        @LoginBasic Member member) {
        Long cartId = cartService.addCart(member, productId);

        return ResponseEntity.created(URI.create("/carts/" + cartId))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCart(@PathVariable final Long productId,
                                           @LoginBasic final Member member) {
        cartService.deleteCart(member, productId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
