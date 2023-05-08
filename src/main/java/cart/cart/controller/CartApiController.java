package cart.cart.controller;

import cart.authorization.AuthorizedMember;
import cart.cart.dto.CartItemDto;
import cart.cart.dto.request.CartItemAddRequest;
import cart.cart.service.CartService;
import cart.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/items")
@Validated
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getItemsOfCart(@AuthorizedMember long memberId) {
        List<ProductDto> items = cartService.findItemsOfCart(memberId);

        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Void> addItemToCart(@AuthorizedMember long memberId, @RequestBody @Valid CartItemAddRequest cartItemAddRequest) {
        final CartItemDto cartItemDto = cartService.addItemToCart(memberId, cartItemAddRequest.getProductId());

        return ResponseEntity.created(URI.create("/cart/items/" + cartItemDto.getProductId())).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItemFromCart(@NotNull(message = "상품 번호를 입력해주세요") @AuthorizedMember long memberId, @PathVariable Long productId) {
        cartService.deleteItemFromCart(memberId, productId);

        return ResponseEntity.noContent().build();
    }
}
