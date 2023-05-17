package cart.controller.api;

import cart.config.AuthenticationPrincipal;
import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.MemberRequest;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> cart(@AuthenticationPrincipal MemberRequest memberRequest) {
        List<CartResponse> carts = cartService.getCartProductByMember(memberRequest);
        return ResponseEntity.ok(carts);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addCart(
            @AuthenticationPrincipal MemberRequest memberRequest,
            @RequestBody CartRequest cartRequest
    ) {
        Long cartId = cartService.addCart(cartRequest.getProductId(), memberRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).body(cartId);
    }

    @DeleteMapping("{cartId}")
    public void deleteCart(
            @PathVariable Long cartId
    ) {
        cartService.deleteCart(cartId);
    }
}
