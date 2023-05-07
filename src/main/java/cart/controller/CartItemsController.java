package cart.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cart.dto.AuthInfo;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.exception.ExceptionCode;
import cart.infra.AuthorizationExtractor;
import cart.infra.CartRequestExtractor;
import cart.service.JwpCartService;

@RequestMapping("/cartItems")
@RestController
public class CartItemsController {
    private final JwpCartService jwpCartService;

    public CartItemsController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addToCart(HttpServletRequest httpServletRequest) throws IOException {
        AuthInfo authInfo = AuthorizationExtractor.extract(httpServletRequest);
        if (authInfo == null) {
            return ResponseEntity.badRequest().body(ExceptionCode.NO_AUTHORIZATION_HEADER);
        }

        CartRequest cartRequest = CartRequestExtractor.extract(httpServletRequest);

        jwpCartService.addProductToCart(authInfo, cartRequest);
        return ResponseEntity.created(URI.create("/cartItems")).build();
    }

    @GetMapping
    public ResponseEntity<Object> findAllCartItems(HttpServletRequest httpServletRequest) {
        AuthInfo authInfo = AuthorizationExtractor.extract(httpServletRequest);
        if (authInfo == null) {
            return ResponseEntity.badRequest().body(ExceptionCode.NO_AUTHORIZATION_HEADER);
        }
        List<CartResponse> all = jwpCartService.findAllCartItems(authInfo);
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Object> deleteCartProduct(HttpServletRequest httpServletRequest,
        @PathVariable Long productId) {
        AuthInfo authInfo = AuthorizationExtractor.extract(httpServletRequest);
        if (authInfo == null) {
            return ResponseEntity.badRequest().body(ExceptionCode.NO_AUTHORIZATION_HEADER);
        }
        jwpCartService.deleteProductFromCart(authInfo, productId);
        return ResponseEntity.ok().build();
    }
}
