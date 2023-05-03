package cart.controller;

import cart.auth.AuthParam;
import cart.auth.UserInfo;
import cart.dto.ProductResponse;
import cart.dto.UserCartRequest;
import cart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/cart")
    public ResponseEntity<Void> addProductToCart(@AuthParam UserInfo userInfo,
                                                 @RequestBody @Valid UserCartRequest userCartRequest) {
        userService.addProductToCart(userInfo, userCartRequest.getProductId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/cart/{id}")
    public ResponseEntity<Void> deleteProductInCart(@AuthParam UserInfo userInfo,
                                                    @PathVariable("id") Long userProductId) {
        userService.removeProductInCart(userInfo, userProductId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/cart/all")
    public ResponseEntity<List<ProductResponse>> getAllProductsInCart(@AuthParam UserInfo userInfo) {
        final List<ProductResponse> products = userService.getAllProductsInCart(userInfo);

        return ResponseEntity.ok(products);
    }
}
