package cart.controller;

import cart.auth.AuthAccount;
import cart.global.annotation.LogIn;
import cart.service.CartCommandService;
import cart.service.CartQueryService;
import cart.service.dto.CartInProductDeleteRequest;
import cart.service.dto.CartInProductRegisterRequest;
import cart.service.dto.ProductSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class CartController {

    private final CartCommandService cartCommandService;
    private final CartQueryService cartQueryService;

    public CartController(final CartCommandService cartCommandService, final CartQueryService cartQueryService) {
        this.cartCommandService = cartCommandService;
        this.cartQueryService = cartQueryService;
    }

    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerProductInCart(
            @LogIn AuthAccount account,
            @RequestBody final CartInProductRegisterRequest cartInProductRegisterRequest
    ) {
        cartCommandService.registerProductInCart(account, cartInProductRegisterRequest);

        return "redirect:/";
    }

    @GetMapping("/carts")
    @ResponseBody
    public List<ProductSearchResponse> showAllProductsInCart(@LogIn AuthAccount account) {
        return cartQueryService.findAllProductsInCart(account);
    }

    @DeleteMapping("/carts")
    public String deleteProductInCart(
            @LogIn AuthAccount account,
            @RequestBody final CartInProductDeleteRequest cartInProductDeleteRequest
    ) {
        cartCommandService.deleteProductInCart(account, cartInProductDeleteRequest);

        return "cart";
    }
}
