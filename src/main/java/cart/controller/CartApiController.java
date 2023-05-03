package cart.controller;

import cart.auth.Auth;
import cart.auth.AuthUserInfo;
import cart.controller.dto.CartProductsRequest;
import cart.controller.dto.ProductInCartResponse;
import cart.entity.vo.Email;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/products")
    public ResponseEntity<List<ProductInCartResponse>> getProductsByUser(@Auth final AuthUserInfo authUserInfo) {
        final Email userEmail = new Email(authUserInfo.getEmail());
        final List<ProductInCartResponse> responseBody = cartService.findAllProductsInCartByUser(userEmail).stream()
                .map(productInCart -> new ProductInCartResponse(
                        productInCart.getId(),
                        productInCart.getProductName(),
                        productInCart.getProductPrice(),
                        productInCart.getProductImageUrl()
                ))
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductFromCart(
            @PathVariable final Long id,
            @Auth AuthUserInfo authUserInfo
    ) {
        final Email userEmail = new Email(authUserInfo.getEmail());
        cartService.deleteCartItem(userEmail, id);

        return ResponseEntity.noContent().build();
    }
}
