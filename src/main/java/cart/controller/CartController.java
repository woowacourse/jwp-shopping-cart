package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cart.controller.dto.CartItemRequest;
import cart.controller.dto.CartItemResponse;
import cart.domain.CartItem;
import cart.service.CartService;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("cart/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody CartItemRequest cartItemRequest) {
        Integer userId = 1;
        cartService.addToCart(userId, cartItemRequest.getProductId());
    }

    @GetMapping("cart/items")
    public List<CartItemResponse> getCartItems() {
        Integer userId = 1;
        final List<CartItem> products = cartService.getItemsOf(userId);
        return mapProducts(products);
    }

    @DeleteMapping("cart/items/{id}")
    public void deleteItem(@PathVariable("id") Integer itemId) {
        Integer userId = 1;
        cartService.deleteFromCart(userId, itemId);
    }

    private List<CartItemResponse> mapProducts(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getImage(),
                        cartItem.getProduct().getPrice())
                ).collect(Collectors.toList());
    }
}
