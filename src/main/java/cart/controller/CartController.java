package cart.controller;

import cart.controller.auth.dto.AuthInfo;
import cart.controller.auth.resolver.BasicAuthenticationPrincipal;
import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
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
    public ResponseEntity<Void> addItem(@BasicAuthenticationPrincipal AuthInfo authInfo,
                                        @RequestBody @Valid final CartRequest cartRequest) {
        cartService.saveCart(authInfo, cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> loadAllItem(@BasicAuthenticationPrincipal AuthInfo authInfo) {
        List<CartResponse> allCart = cartService.loadAllCart(authInfo);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(allCart);
    }
}
