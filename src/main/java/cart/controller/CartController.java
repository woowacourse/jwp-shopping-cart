package cart.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.auth.Extractor;
import cart.dto.product.ProductResponse;
import cart.dto.user.UserRequest;
import cart.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final Extractor extractor;
    private final CartService cartService;

    public CartController(Extractor extractor, CartService cartService) {
        this.extractor = extractor;
        this.cartService = cartService;
    }

    @GetMapping
    public String cart() {
        return "cart";
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> products(@RequestHeader HttpHeaders header) {
        final UserRequest userRequest = extractor.extractUser(header);
        return ResponseEntity.ok(cartService.findAllProductsInCart(userRequest));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> removeProductInCart(
            @RequestHeader HttpHeaders header,
            @PathVariable Long productId
    ) {
        final UserRequest userRequest = extractor.extractUser(header);
        cartService.removeProductInCart(userRequest, productId);
        return ResponseEntity.noContent().build();
    }
}
