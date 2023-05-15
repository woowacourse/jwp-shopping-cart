package cart.controller.api;

import cart.auth.AuthenticationPrincipal;
import cart.dto.CartItemDto;
import cart.dto.MemberDto;
import cart.dto.request.CartAddRequest;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid CartAddRequest cartAddRequest, @AuthenticationPrincipal MemberDto member) {
        long cartId = cartService.add(cartAddRequest, member);
        return ResponseEntity
                .created(URI.create("/cart/" + cartId))
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable long cartId, @AuthenticationPrincipal MemberDto member) {
        cartService.delete(cartId, member);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> findAll(@AuthenticationPrincipal MemberDto member) {
        List<CartItemDto> allItems = cartService.findAllByMemberId(member);
        return ResponseEntity
                .ok()
                .body(allItems);
    }
}
