package cart.cartitems.controller;

import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import cart.cartitems.service.CartItemsService;
import cart.infrastructure.AuthInfo;
import cart.product.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart/items")
public class CartItemsApiController {

    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemsApiController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getCartItems(HttpServletRequest request) {
        final AuthInfo authInfo = (AuthInfo) request.getAttribute("authInfo");

        List<ProductDto> items = cartItemsService.findItemsOfCart(authInfo);

        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Void> addItemToCart(HttpServletRequest request, @RequestBody @Valid CartItemAddRequest cartItemAddRequest) {
        final AuthInfo authInfo = (AuthInfo) request.getAttribute("authInfo");

        final CartItemDto cartItemDto = cartItemsService.addItemToCart(authInfo, cartItemAddRequest);

        return ResponseEntity.created(URI.create("/cart/items/" + cartItemDto.getProductId())).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItemFromCart(HttpServletRequest request, @PathVariable Long productId) {
        final AuthInfo authInfo = (AuthInfo) request.getAttribute("authInfo");

        cartItemsService.deleteItemFromCart(authInfo, productId);

        return ResponseEntity.noContent().build();
    }
}
