package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProductsOfCart(final AuthInfo authInfo) {
        final List<ProductResponse> products = cartService.showProductsBy(authInfo);

        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(final AuthInfo authInfo, @PathVariable long productId) {
        cartService.addToCart(authInfo, productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(final AuthInfo authInfo, @PathVariable long productId) {
        cartService.deleteProductBy(authInfo, productId);
    }
}
