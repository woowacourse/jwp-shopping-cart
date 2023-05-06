package cart.controller;

import cart.domain.cartitem.CartItem;
import cart.domain.cartitem.CartItemService;
import cart.dto.CartItemDto;
import cart.infratstructure.AuthenticationPrincipal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CartItemDto> get(@AuthenticationPrincipal Long loginMemberId) {
        return cartItemService.findAllByMemberId(loginMemberId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{productId}")
    public void create(@PathVariable Long productId, @AuthenticationPrincipal Long loginMemberId) {
        cartItemService.add(new CartItem(loginMemberId, productId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long cartItemId, @AuthenticationPrincipal Long loginMemberId) {
        cartItemService.deleteById(loginMemberId, cartItemId);
    }
}
