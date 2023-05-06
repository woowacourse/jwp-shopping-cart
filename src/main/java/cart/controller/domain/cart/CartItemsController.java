package cart.controller.domain.cart;

import cart.auth.AuthenticationPrincipal;
import cart.dto.LoginDto;
import cart.dto.response.CartItemResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemsController {

    private final CartService cartService;

    public CartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponse> getCartItems(@AuthenticationPrincipal LoginDto loginDto) {
        return cartService.findAllCartItems(loginDto.getMemberId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@AuthenticationPrincipal LoginDto loginDto, @PathVariable int id) {
        cartService.deleteCartItem(id, loginDto.getMemberId());
    }
}
