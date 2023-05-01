package cart.controller;

import cart.dto.MemberAuthDto;
import cart.dto.response.CartProductResponseDto;
import cart.service.CartService;
import cart.util.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/carts")
@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartProductResponseDto>> findCartItemsForMember(@Member MemberAuthDto memberAuthDto) {
        final List<CartProductResponseDto> response = cartService.findCartItemsForMember(memberAuthDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> putInCart(@RequestParam Long productId, @Member MemberAuthDto memberAuthDto) {
        final Long cartId = cartService.putInCart(productId, memberAuthDto);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable(name = "id") Long cartId, @Member MemberAuthDto memberAuthDto) {
        cartService.removeCartItem(cartId, memberAuthDto);
        return ResponseEntity.noContent().build();
    }
}

