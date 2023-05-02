package cart.controller;

import cart.common.CustomMember;
import cart.dto.MemberRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartsRestController {
    private CartService cartService;

    public CartsRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ProductResponseDto>> getCarts(@CustomMember MemberRequestDto member) {
        List<ProductResponseDto> carts = cartService.findAll(new MemberRequestDto(member.getEmail(), member.getPassword()));
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/carts/{id}")
    public ResponseEntity<Void> createCarts(@CustomMember MemberRequestDto member, @PathVariable Long id) {
        cartService.save(new MemberRequestDto(member.getEmail(), member.getPassword()), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCarts(@CustomMember MemberRequestDto member, @PathVariable Long id) {
        cartService.delete(new MemberRequestDto(member.getEmail(), member.getPassword()), id);
        return ResponseEntity.noContent().build();
    }
}
