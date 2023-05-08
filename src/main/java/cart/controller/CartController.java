package cart.controller;

import cart.auth.AuthMember;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findByMemberId(@AuthMember Long memberId) {
        List<CartResponse> cartResponse = cartService.findAllByMemberId(memberId);
        return ResponseEntity.ok().body(cartResponse);
    }

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody @Valid CartRequest cartRequest, @AuthMember Long memberId) {
        CartResponse cartResponse = cartService.create(cartRequest, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponse> update(@PathVariable Long cartId, @RequestBody @Valid CartRequest cartRequest, @AuthMember Long memberId) {
        CartResponse updated = cartService.update(cartRequest, cartId, memberId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartId, @AuthMember Long memberId) {
        cartService.deleteById(cartId, memberId);
        return ResponseEntity.ok().build();
    }
}
