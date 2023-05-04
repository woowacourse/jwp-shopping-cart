package cart.web.controller.cart;

import cart.domain.cart.CartProduct;
import cart.domain.user.User;
import cart.web.controller.auth.Login;
import cart.web.controller.cart.dto.CartResponse;
import cart.web.service.CartService;
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

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(@Login User user,
                                           @PathVariable Long productId) {
        System.out.println("user.getUserEmailValue() = " + user.getUserEmailValue());
        System.out.println("user.getUserPasswordValue( = " + user.getUserPasswordValue());
        System.out.println("user.getId( = " + user.getId());

        final Long addedProductId = cartService.add(user, productId);

        return ResponseEntity.created(URI.create("/cart/" + addedProductId)).build();
    }

    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<Void> deleteProduct(@Login User user,
                                              @PathVariable Long cartProductId) {
        cartService.delete(user, cartProductId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartResponse>> getProducts(@Login User user) {
        final List<CartProduct> cartProducts = cartService.getCartProducts(user);
        final List<CartResponse> productResponses = cartProducts.stream()
                .map(product -> new CartResponse(product.getCartProductId(), product.getProductId(), product.getProductNameValue(),
                        product.getImageUrlValue(), product.getPriceValue(), product.getCategory()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponses);
    }
}
