package cart.cartitems.controller;

import cart.authorization.AuthInfo;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import cart.cartitems.service.CartService;
import cart.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/items")
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getItemsOfCart(AuthInfo authInfo) {
        List<ProductDto> items = cartService.findItemsOfCart(authInfo);

        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Void> addItemToCart(AuthInfo authInfo, @RequestBody @Valid CartItemAddRequest cartItemAddRequest) {
        final CartItemDto cartItemDto = cartService.addItemToCart(authInfo, cartItemAddRequest);

        return ResponseEntity.created(URI.create("/cart/items/" + cartItemDto.getProductId())).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItemFromCart(AuthInfo authInfo, @PathVariable Long productId) {
        cartService.deleteItemFromCart(authInfo, productId);

        return ResponseEntity.noContent().build();
    }
}
