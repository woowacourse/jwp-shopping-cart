package cart.web.controller.cart;

import cart.domain.cart.CartProduct;
import cart.web.controller.auth.AuthorizedUser;
import cart.web.controller.cart.dto.AuthCredentials;
import cart.web.controller.cart.dto.CartResponse;
import cart.web.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(@AuthorizedUser AuthCredentials authCredentials,
                                           @PathVariable Long productId) {
        final Long addedProductId = cartService.add(authCredentials, productId);

        return ResponseEntity.created(URI.create("/cart/" + addedProductId)).build();
    }

    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<Void> deleteProduct(@AuthorizedUser AuthCredentials authCredentials,
                                              @PathVariable Long cartProductId) {
        cartService.delete(authCredentials, cartProductId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartResponse>> getProducts(@AuthorizedUser AuthCredentials authCredentials) {
        final List<CartProduct> cartProducts = cartService.getCartProducts2(authCredentials);
        final List<CartResponse> productResponses = cartProducts.stream()
                .map(product -> new CartResponse(product.getCartProductId(), product.getProductId(), product.getProductNameValue(),
                        product.getImageUrlValue(), product.getPriceValue(), product.getCategory()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponses);
    }
}
