package cart.web.cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cart.domain.persistence.ProductDto;
import cart.domain.cart.CartService;
import cart.web.cart.dto.AuthInfo;
import cart.web.cart.dto.CartResponse;
import cart.web.cart.dto.PostCartRequest;

@RestController
public class CartApiController {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final CartService cartService;

    public CartApiController(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final CartService cartService) {
        this.authorizationExtractor = authorizationExtractor;
        this.cartService = cartService;
    }

    @GetMapping(path = "/cart", headers = "authorization")
    public List<CartResponse> getAllCartProducts(final HttpServletRequest request) {
        final AuthInfo authInfo = authorizationExtractor.extract(request);
        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        final List<ProductDto> productDtos = cartService.findProductsByMember(email, password);
        return productDtos.stream()
            .map(CartResponse::from)
            .collect(Collectors.toList());
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody @Valid PostCartRequest body, final HttpServletRequest request) {
        final AuthInfo authInfo = authorizationExtractor.extract(request);
        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        final long productId = body.getProductId();
        cartService.addProductByMember(productId, email, password);
    }

    @DeleteMapping("/cart/{cartId}")
    public void deleteFromCart(@PathVariable Integer cartId) {
        cartService.deleteByCartId(cartId);
    }
}
