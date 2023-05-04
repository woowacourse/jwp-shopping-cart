package cart.controller;

import cart.common.AuthInfo;
import cart.domain.cart.service.CartService;
import cart.dto.CartCreateRequest;
import cart.dto.CartResponse;
import cart.dto.MemberInformation;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartResponse>> getCarts(
        @AuthInfo final MemberInformation memberInformation) {
        final List<CartResponse> responses = cartService.findByEmail(memberInformation.getEmail());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<Void> addCart(@RequestParam final Long productId,
        @AuthInfo final MemberInformation memberInformation) {
        final CartCreateRequest request = new CartCreateRequest(productId,
            memberInformation.getEmail());
        cartService.create(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteCart(@RequestParam final Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
