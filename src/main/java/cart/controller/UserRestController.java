package cart.controller;

import cart.auth.AuthParam;
import cart.auth.UserInfo;
import cart.dto.UserCartRequest;
import cart.dto.UserCartResponse;
import cart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public final class UserRestController {

    private final UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/cart")
    public ResponseEntity<Void> addProductToCart(@AuthParam UserInfo userInfo,
                                                 @RequestBody @Valid UserCartRequest userCartRequest) {
        final Long productId = userService.addProductToCart(userInfo, userCartRequest.getProductId());

        return ResponseEntity.created(URI.create("/user/cart/" + productId)).build();
    }

    @DeleteMapping("/user/cart/{id}")
    public ResponseEntity<Void> deleteProductInCart(@AuthParam UserInfo userInfo,
                                                    @PathVariable("id") Long userProductId) {
        userService.deleteProductInCart(userInfo, userProductId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/cart")
    public ResponseEntity<List<UserCartResponse>> getAllProductsInCart(@AuthParam UserInfo userInfo) {
        final List<UserCartResponse> products = userService.getAllProductsInCart(userInfo);

        return ResponseEntity.ok(products);
    }
}
