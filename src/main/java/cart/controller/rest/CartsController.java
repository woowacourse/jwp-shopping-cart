package cart.controller.rest;

import cart.auth.Auth;
import cart.domain.member.Member;
import cart.dto.response.CartResponse;
import cart.dto.response.ItemResponse;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/api/cart")
public class CartsController {

    private final CartService cartService;

    public CartsController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<ItemResponse> createItem(
            @Auth Member member,
            @RequestParam("product-id") Long productId) {

        ItemResponse itemResponse = cartService.createItem(member.getId(), productId);

        URI createdUri = ServletUriComponentsBuilder
                .fromPath("/items/{id}")
                .buildAndExpand(itemResponse.getId())
                .toUri();

        return ResponseEntity.created(createdUri).body(itemResponse);
    }

    @GetMapping("/items")
    public ResponseEntity<CartResponse> readItemsByMember(@Auth Member member) {
        CartResponse cartResponse = cartService.readAllItemsByMemberId(member.getId());

        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") @NotNull Long itemId) {
        cartService.deleteItemById(itemId);

        return ResponseEntity.noContent().build();
    }
}
