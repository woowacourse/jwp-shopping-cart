package cart.controller;

import cart.auth.resolver.BasicAuthenticationPrincipal;
import cart.controller.dto.auth.AuthInfo;
import cart.controller.dto.request.CartRequest;
import cart.controller.dto.response.CartResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCart(@BasicAuthenticationPrincipal AuthInfo authInfo,
                                        @RequestBody @Valid final CartRequest cartRequest) {
        cartService.saveCart(authInfo, cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> loadAllCart(@BasicAuthenticationPrincipal AuthInfo authInfo) {
        List<CartResponse> allCart = cartService.loadAllCart(authInfo);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(allCart);
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable final Long cartId) {
        cartService.deleteItem(cartId);
        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/"))
                             .build();
    }
}
