package cart.controller;

import cart.dto.ResponseProductDto;
import cart.service.CartService;
import cart.service.ProductService;
import cart.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class CartApiController {

    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    public CartApiController(final UserService userService, final CartService cartService, final ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/carts")
    public List<ResponseProductDto> readCarts(@RequestHeader("authorization") final String authorization) {
        final Credentials credentials = getCredentials(authorization);

        final Long userId = userService.findIdByEmail(credentials.getEmail());
        final List<Long> productIds = cartService.findProductIdsByUserId(userId);
        return productService.findByIds(productIds);
    }

    private Credentials getCredentials(final String authorization) {
        if (authorization == null || !authorization.toLowerCase().startsWith("basic")) {
            throw new IllegalArgumentException();
        }
        final String base64Credentials = authorization.substring("Basic".length()).trim();
        final byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
        final String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);
        final List<String> values = Arrays.asList(credentials.split(":", 2));

        final String email = values.get(0);
        final String password = values.get(1);

        return new Credentials(email, password);
    }
}
