package cart.controller;

import cart.auth.Auth;
import cart.auth.AuthUserInfo;
import cart.controller.dto.CartProductsRequest;
import cart.entity.vo.Email;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addToCart(
            @Valid @RequestBody final CartProductsRequest requestBody,
            @Auth final AuthUserInfo authUserInfo
    ) {
        final Email userEmail = new Email(authUserInfo.getEmail());
        final long generatedCartItemId = cartService.addProductToUser(userEmail, requestBody.getProductId());

        return ResponseEntity.created(URI.create("/cart/" + generatedCartItemId)).build();
    }
}
