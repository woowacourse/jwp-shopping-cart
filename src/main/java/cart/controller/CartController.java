package cart.controller;

import cart.authentication.AuthenticationPrincipal;
import cart.controller.dto.AuthInfo;
import cart.controller.dto.request.AddCartRequest;
import cart.controller.dto.response.ItemResponse;
import cart.service.CartService;
import cart.service.dto.CartDto;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAllCarts(@AuthenticationPrincipal AuthInfo authInfo) {
        CartDto cartDto = cartService.findCart(authInfo.getEmail());
        List<ItemResponse> itemResponses = cartDto.getItemDtos()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(itemResponses);
    }

    @PostMapping
    public ResponseEntity<CartDto> addCartItem(
            @RequestBody @Valid AddCartRequest addCartRequest,
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        CartDto cartDto = cartService.addItem(authInfo.getEmail(), addCartRequest.getId());

        return ResponseEntity.created(URI.create("/carts"))
                .body(cartDto);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId, @AuthenticationPrincipal AuthInfo authInfo) {
        cartService.deleteCartItem(authInfo.getEmail(), cartId);

        return ResponseEntity.noContent().build();
    }
}
