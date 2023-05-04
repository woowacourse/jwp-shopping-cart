package cart.web.cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cart.domain.cart.CartService;
import cart.domain.persistence.ProductDto;
import cart.web.argumentResolver.AuthorizeMember;
import cart.web.argumentResolver.AuthorizedMember;
import cart.web.cart.dto.CartResponse;
import cart.web.cart.dto.PostCartRequest;

@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/cart", headers = "authorization")
    public List<CartResponse> getAllCartProducts(@AuthorizeMember AuthorizedMember authorizedMember) {
        final String email = authorizedMember.getEmail();
        final String password = authorizedMember.getPassword();
        final List<ProductDto> productDtos = cartService.findProductsByMember(email, password);
        return productDtos.stream()
            .map(CartResponse::from)
            .collect(Collectors.toList());
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody @Valid final PostCartRequest body,
        @AuthorizeMember AuthorizedMember authorizedMember) {
        final long productId = body.getProductId();
        final String email = authorizedMember.getEmail();
        final String password = authorizedMember.getPassword();
        cartService.addProductByMember(productId, email, password);
    }

    @DeleteMapping("/cart/{cartId}")
    public void deleteFromCart(@PathVariable final Integer cartId, @AuthorizeMember AuthorizedMember authorizedMember) {
        final String email = authorizedMember.getEmail();
        final String password = authorizedMember.getPassword();
        cartService.deleteCartIdFromMember(cartId, email, password);
    }
}
