package cart.controller;

import cart.global.BasicAuthorizationDecoder;
import cart.service.CartService;
import cart.service.dto.ProductSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("/carts")
    @ResponseBody
    public List<ProductSearchResponse> showAllProductsInCart(final HttpServletRequest httpServletRequest) {
        return cartService.findAllProductsInCart(BasicAuthorizationDecoder.decode(httpServletRequest));
    }

    @GetMapping("/cart")
    public String showCartForm() {
        return "cart";
    }

    @DeleteMapping("/carts/products/{product-id}")
    public String deleteProductInCart(final HttpServletRequest httpServletRequest,
                                      @PathVariable("product-id") final Long productId) {

        cartService.deleteProductInCart(
                BasicAuthorizationDecoder.decode(httpServletRequest),
                productId
        );

        return "cart";
    }
}
