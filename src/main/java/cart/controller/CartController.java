package cart.controller;

import cart.dto.AuthInfoRequest;
import cart.mvcconfig.annotation.AuthenticationPrincipal;
import cart.repository.entity.ProductEntity;
import cart.service.CartService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String readCart() {
        return "cart";
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<ProductEntity>> readProductsInCart(@AuthenticationPrincipal final AuthInfoRequest authInfoRequest) {
        final List<ProductEntity> cartItems = cartService.findCartItemsByAuthInfo(authInfoRequest);
        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("/cart")
    public ResponseEntity<Void> addProductInCart(@RequestParam final Long productId,
                                                 @AuthenticationPrincipal final AuthInfoRequest authInfoRequest) {
        cartService.addProductByAuthInfo(productId, authInfoRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Void> deleteProductInCart(@RequestParam final Long productId,
                                                    @AuthenticationPrincipal final AuthInfoRequest authInfoRequest) {
        cartService.deleteProductByAuthInfo(productId, authInfoRequest);
        return ResponseEntity.ok().build();
    }
}
