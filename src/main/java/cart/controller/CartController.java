package cart.controller;

import cart.auth.resolver.BasicAuthenticationPrincipal;
import cart.controller.dto.auth.AuthInfoDto;
import cart.controller.dto.response.CartResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("{itemId}")
    public ResponseEntity<Void> addCart(@BasicAuthenticationPrincipal AuthInfoDto authInfoDto,
                                        @PathVariable final Long itemId) {
        cartService.saveCart(authInfoDto, itemId);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> loadAllCart(@BasicAuthenticationPrincipal AuthInfoDto authInfoDto) {
        List<CartResponse> allCart = cartService.loadAllCart(authInfoDto);
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
