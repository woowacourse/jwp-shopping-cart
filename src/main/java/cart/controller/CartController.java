package cart.controller;

import cart.configuration.AuthenticationPrincipal;
import cart.controller.dto.AddCartRequest;
import cart.controller.dto.AuthInfo;
import cart.controller.dto.CartResponse;
import cart.service.CartService;
import cart.service.dto.CartDto;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findAllCarts(@AuthenticationPrincipal AuthInfo authInfo) {
        List<CartResponse> cartResponses = cartService.findAllByEmail(authInfo.getEmail())
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cartResponses);
    }

    @PostMapping
    public ResponseEntity<CartResponse> addCart(
            @RequestBody @Valid AddCartRequest addCartRequest,
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        CartDto cartDto = cartService.add(authInfo.getEmail(), addCartRequest.getId());

        return ResponseEntity.created(URI.create("/carts/" + cartDto.getCartId()))
                .body(CartResponse.from(cartDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id, @AuthenticationPrincipal AuthInfo authInfo) {
        cartService.delete(id, authInfo.getEmail());

        return ResponseEntity.noContent().build();
    }
}
