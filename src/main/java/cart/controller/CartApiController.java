package cart.controller;

import cart.dto.AuthInfoRequest;
import cart.mvcconfig.annotation.AuthenticationPrincipal;
import cart.repository.entity.ProductEntity;
import cart.service.CartService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<ProductEntity>> readProductsInCart(
            @AuthenticationPrincipal final AuthInfoRequest authInfoRequest) {
        final List<ProductEntity> cartItems = cartService.findProductsByAuthInfo(authInfoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(cartItems);
    }

    @PostMapping
    public ResponseEntity<Void> addProductInCart(@RequestParam final Long productId,
                                                 @AuthenticationPrincipal final AuthInfoRequest authInfoRequest) {
        cartService.addProductByAuthInfo(productId, authInfoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductInCart(@RequestParam final Long productId,
                                                    @AuthenticationPrincipal final AuthInfoRequest authInfoRequest) {
        cartService.deleteProductByAuthInfo(productId, authInfoRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
