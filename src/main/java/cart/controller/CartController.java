package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.service.CartService;
import cart.util.BasicAuthorizationExtractor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String myCart() {
        return "cart";
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProductsOfCart(@RequestHeader(value = "Authorization") String credentials) {
        final AuthInfo authInfo = BasicAuthorizationExtractor.extract(credentials, "/cart");

        final List<ProductResponse> products = cartService.showProductsBy(authInfo);

        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@RequestHeader(value = "Authorization") String credentials, @PathVariable long productId) {
        final AuthInfo authInfo = BasicAuthorizationExtractor.extract(credentials, "/cart");

        cartService.addToCart(authInfo, productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestHeader(value = "Authorization") String credentials, @PathVariable long productId) {
        final AuthInfo authInfo = BasicAuthorizationExtractor.extract(credentials, "/cart");

        cartService.deleteProductBy(authInfo, productId);
    }
}
