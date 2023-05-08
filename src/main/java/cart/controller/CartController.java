package cart.controller;

import cart.auth.AuthInfo;
import cart.domain.cart.dto.CartDto;
import cart.dto.CartResponse;
import cart.domain.cart.service.CartService;
import cart.domain.member.dto.MemberDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCarts(
        @AuthInfo final MemberDto memberDto) {
        final List<CartDto> cartDtos = cartService.findByEmail(memberDto.getEmail());
        final List<CartResponse> responses = cartDtos.stream()
            .map(CartResponse::of)
            .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestParam final Long productId,
        @AuthInfo final MemberDto memberDto) {
        cartService.create(productId, memberDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestParam final Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
