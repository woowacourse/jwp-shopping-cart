package cart.controller;

import cart.controller.authentication.AuthInfo;
import cart.controller.authentication.AuthenticationPrincipal;
import cart.dto.ResponseProductDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public List<ResponseProductDto> readCart(@AuthenticationPrincipal AuthInfo authInfo) {
        return cartService.findCartProducts(authInfo);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> createCart(@AuthenticationPrincipal AuthInfo authInfo, @PathVariable final Long productId) {
        cartService.insert(authInfo, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal AuthInfo authInfo, @PathVariable final Long productId) {
        cartService.delete(authInfo, productId);
        return ResponseEntity.ok().build();
    }
}
