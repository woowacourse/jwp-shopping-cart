package cart.controller;

import cart.auth.BasicAuthorizationExtractor;
import cart.auth.UserInfo;
import cart.dto.ProductResponse;
import cart.dto.UserCartRequest;
import cart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;
    private final BasicAuthorizationExtractor authorizationExtractor;

    public UserRestController(final UserService userService, final BasicAuthorizationExtractor authorizationExtractor) {
        this.userService = userService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @PostMapping("/user/cart")
    public ResponseEntity<Void> addProductToCart(HttpServletRequest request,
                                                 @RequestBody UserCartRequest userCartRequest) {
        final UserInfo userInfo = authorizationExtractor.getUserInfo(request);
        userService.addProductToCart(userInfo, userCartRequest.getProductId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/cart/{id}")
    public ResponseEntity<Void> deleteProductInCart(HttpServletRequest request,
                                                    @PathVariable("id") Long userProductId) {
        final UserInfo userInfo = authorizationExtractor.getUserInfo(request);
        userService.removeProductInCart(userInfo, userProductId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/cart/all")
    public ResponseEntity<List<ProductResponse>> getAllProductsInCart(HttpServletRequest request) {
        final UserInfo userInfo = authorizationExtractor.getUserInfo(request);
        final List<ProductResponse> products = userService.getAllProductsInCart(userInfo);

        return ResponseEntity.ok(products);
    }
}
