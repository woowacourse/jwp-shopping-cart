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
    public ResponseEntity<ProductsResponseDto> findMemberCartItems(@LoginBasic final Member member) {
        return ResponseEntity.ok(cartService.findAllCartItems(member));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCartItem(@PathVariable final Long productId,
                                            @LoginBasic Member member) {
        Long cartId = cartService.addCartItem(member, productId);

        return ResponseEntity.created(URI.create("/carts/" + cartId))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long productId,
                                               @LoginBasic final Member member) {
        cartService.deleteCartItem(member, productId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
