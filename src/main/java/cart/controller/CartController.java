package cart.controller;

import cart.controller.support.BasicAuth;
import cart.dto.ApiResponse;
import cart.dto.BasicCredentials;
import cart.dto.CartItemCreateRequest;
import cart.dto.CartItemResponse;
import cart.service.CartService;
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

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResponse<List<CartItemResponse>> readCartItems(@BasicAuth BasicCredentials basicCredentials) {
        List<CartItemResponse> cartItems = cartService.getCartItems(basicCredentials.getEmail()).stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.of(HttpStatus.OK, cartItems);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse<Void> createCartItem(@RequestBody @Valid CartItemCreateRequest request,
                                      @BasicAuth BasicCredentials basicCredentials) {
        cartService.addCartItem(basicCredentials.getEmail(), request.getProductId());
        return ApiResponse.from(HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResponse<Void> deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId,
                                      @BasicAuth BasicCredentials basicCredentials) {
        cartService.deleteCartItem(cartItemId);
        return ApiResponse.from(HttpStatus.OK);
    }

}
