package cart.controller;

import cart.domain.CartItem;
import cart.dto.ApiDataResponse;
import cart.dto.ApiResponse;
import cart.dto.CartItemCreateRequest;
import cart.dto.CartItemResponse;
import cart.service.CartService;
import cart.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/cartItems")
public class CartController {

    private UserService userService;
    private CartService cartService;

    public CartController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    // TODO: 헤더를 읽어서 이메일과 비밀번호를 가져오는 기능
    String email = "rosie@wooteco.com";
    String password = "1234";

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ApiDataResponse<List<CartItemResponse>> readCartItems() {
        userService.authorizeUser(email, password);

        List<CartItemResponse> cartItems = cartService.getCartItems(email).stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return ApiDataResponse.of(HttpStatus.OK, cartItems);
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse createCartItem(@RequestBody @Valid CartItemCreateRequest request) {
        userService.authorizeUser(email, password);

        cartService.addCartItem(email, request.getProductId());
        return ApiResponse.from(HttpStatus.CREATED);
    }

}
