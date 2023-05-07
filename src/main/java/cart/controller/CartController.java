package cart.controller;

import cart.auth.AuthInfo;
import cart.auth.BasicAuth;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> cartList(@BasicAuth AuthInfo authInfo) {
        System.out.println(authInfo.getEmail());
        List<CartResponse> response = cartService.findAllByEmail(authInfo.getEmail());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
