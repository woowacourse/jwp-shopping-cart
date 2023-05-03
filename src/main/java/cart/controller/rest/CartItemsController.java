package cart.controller.rest;

import cart.auth.AuthenticationPrincipal;
import cart.dto.LoginDto;
import cart.dto.response.CartItemResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemsController {

    private final CartService cartService;

    public CartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart-items")
    public List<CartItemResponse> getCartItems(@AuthenticationPrincipal LoginDto loginDto, Model model) {
        return cartService.findAllCartItems(loginDto.getMemberId());
    }

    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@AuthenticationPrincipal LoginDto loginDto, @PathVariable int id) {
        cartService.deleteCartItem(id, loginDto.getMemberId());
    }
}
