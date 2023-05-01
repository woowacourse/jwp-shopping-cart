package cart.controller;

import cart.global.BasicAuthorizationDecoder;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts/products/{product-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerProductInCart(final HttpServletRequest httpServletRequest,
                                        @PathVariable("product-id") final Long productId) {

        cartService.registerProductInCart(
                BasicAuthorizationDecoder.decode(httpServletRequest),
                productId
        );

        return "redirect:/";
    }
}
