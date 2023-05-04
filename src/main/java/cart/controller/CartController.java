package cart.controller;

import cart.controller.support.BasicAuth;
import cart.dto.ApiDataResponse;
import cart.dto.ApiResponse;
import cart.dto.AuthPayload;
import cart.dto.CartItemCreateRequest;
import cart.dto.CartItemResponse;
import cart.service.CartService;
import cart.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/cartItems")
public class CartController {

    private final UserService userService;
    private final CartService cartService;

    public CartController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ApiDataResponse<List<CartItemResponse>> readCartItems(@BasicAuth AuthPayload authPayload) {
        userService.authorizeUser(authPayload.getEmail(), authPayload.getPassword());

        List<CartItemResponse> cartItems = cartService.getCartItems(authPayload.getEmail()).stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return ApiDataResponse.of(HttpStatus.OK, cartItems);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse createCartItem(@RequestBody @Valid CartItemCreateRequest request,
                                      @BasicAuth AuthPayload authPayload) {
        userService.authorizeUser(authPayload.getEmail(), authPayload.getPassword());

        cartService.addCartItem(authPayload.getEmail(), request.getProductId());
        return ApiResponse.from(HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResponse deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId,
                                      @BasicAuth AuthPayload authPayload) {
        userService.authorizeUser(authPayload.getEmail(), authPayload.getPassword());

        cartService.deleteCartItem(cartItemId);
        return ApiResponse.from(HttpStatus.OK);
    }

}
