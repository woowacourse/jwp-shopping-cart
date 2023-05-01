package cart.controller;

import cart.dto.MemberAuthDto;
import cart.dto.response.CartProductResponseDto;
import cart.service.CartService;
import cart.util.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> putInCart(@RequestParam Long productId, @Member MemberAuthDto memberAuthDto) {
        final Long savedCartId = cartService.putInCart(memberAuthDto, productId);
        return ResponseEntity.created(URI.create("/carts/" + savedCartId)).build();
    }

    @GetMapping
    public ResponseEntity<List<CartProductResponseDto>> findCartProductsByMember(@Member MemberAuthDto memberAuthDto) {
        final List<CartProductResponseDto> response = cartService.findCartProductsByMember(memberAuthDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
