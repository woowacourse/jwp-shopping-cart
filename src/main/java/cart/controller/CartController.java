package cart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cart.annotation.Auth;
import cart.annotation.CartReq;
import cart.dto.AuthInfo;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.service.JwpCartService;

@RequestMapping("/cart-items")
@RestController
public class CartController {
    private final JwpCartService jwpCartService;

    public CartController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addToCart(@Auth AuthInfo authInfo, @CartReq CartRequest cartRequest) {
        jwpCartService.addProductToCart(authInfo, cartRequest);
        return ResponseEntity.created(URI.create("/cart-items")).build();
    }

    @GetMapping
    public ResponseEntity<Object> findAllCartItems(@Auth AuthInfo authInfo) {
        List<CartResponse> all = jwpCartService.findAllCartItems(authInfo);
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Object> deleteCartProduct(@Auth AuthInfo authInfo, @PathVariable Long productId) {
        jwpCartService.deleteProductFromCart(authInfo, productId);
        return ResponseEntity.ok().build();
    }
}
