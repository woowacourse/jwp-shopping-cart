package cart.controller;

import cart.controller.dto.CartProductsRequest;
import cart.entity.vo.Email;
import cart.service.CartService;
import cart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final UserService userService;
    private final CartService cartService;

    public CartApiController(final UserService userService, final CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addToCart(
            @Valid @RequestBody final CartProductsRequest requestBody,
            final HttpServletRequest request
    ) {
        final String authorization = request.getHeader("Authorization");

        final String BASIC_AUTH_PREFIX = "Basic";
        final String DELIMITER = ":";

        final String authValue = authorization.substring(BASIC_AUTH_PREFIX.length()).trim();
        final byte[] decodedAuthBytes = Base64.getDecoder().decode(authValue);
        final String decodedAuth = new String(decodedAuthBytes);

        final String[] credential = decodedAuth.split(DELIMITER);
        final String email = credential[0];
        final String password = credential[1];

        userService.validateUser(email, password);

        final long generatedCartItemId = cartService.addProductToUser(new Email(email), requestBody.getProductId());


        return ResponseEntity.created(URI.create("/cart/" + generatedCartItemId)).build();
    }
}
