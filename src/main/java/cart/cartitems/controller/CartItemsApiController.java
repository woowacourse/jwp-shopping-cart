package cart.cartitems.controller;

import cart.authorization.AuthInfo;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import cart.cartitems.service.CartItemsService;
import cart.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/items")
public class CartItemsApiController {

    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemsApiController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getCartItems(AuthInfo authInfo) {
        List<ProductDto> items = cartItemsService.findItemsOfCart(authInfo);

        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Void> addItemToCart(AuthInfo authInfo, @RequestBody @Valid CartItemAddRequest cartItemAddRequest) {
        final CartItemDto cartItemDto = cartItemsService.addItemToCart(authInfo, cartItemAddRequest);

        return ResponseEntity.created(URI.create("/cart/items/" + cartItemDto.getProductId())).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItemFromCart(AuthInfo authInfo, @PathVariable Long productId) {
        cartItemsService.deleteItemFromCart(authInfo, productId);

        return ResponseEntity.noContent().build();
    }
}
