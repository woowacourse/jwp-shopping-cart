package cart.controller;

import cart.common.CustomMember;
import cart.domain.Member;
import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartsRestController {
    private CartService cartService;

    public CartsRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponseDto>> getCarts(@CustomMember Member member) {
        List<CartResponseDto> carts = cartService.findAll(member.getEmail());
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/carts/{id}")
    public ResponseEntity<Void> createCarts(@CustomMember Member member, @PathVariable Long id) {
        cartService.save(new CartRequestDto(member.getEmail(), id));
        return ResponseEntity.noContent().build();
    }


}
