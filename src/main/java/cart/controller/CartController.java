package cart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.controller.argumentresolver.AuthenticationPrincipal;
import cart.dto.cart.CartProductResponse;
import cart.dto.user.UserRequest;
import cart.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cart() {
        return "cart";
    }

    @GetMapping("/products")
    public ResponseEntity<List<CartProductResponse>> products(@AuthenticationPrincipal UserRequest userRequest) {
        return ResponseEntity.ok(cartService.findAllProductsInCart(userRequest));
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Void> addProductToCart(
            @AuthenticationPrincipal UserRequest userRequest,
            @PathVariable Long productId
    ) {
        final Long cartId = cartService.addProduct(userRequest, productId);
        return ResponseEntity.created(URI.create("/cart/product/" + cartId)).build();
    }

    @DeleteMapping("/product/{cartId}")
    public ResponseEntity<Void> removeProductInCart(
            @AuthenticationPrincipal UserRequest userRequest,
            @PathVariable Long cartId
    ) {
        cartService.removeProductInCart(userRequest, cartId);
        return ResponseEntity.noContent().build();
    }
}
