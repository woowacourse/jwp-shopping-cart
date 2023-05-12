package cart.controller;

import cart.domain.Cart;
import cart.dto.AddCartRequestDto;
import cart.dto.auth.AuthInfo;
import cart.dto.response.ResponseCartProductDto;
import cart.service.CartService;
import cart.ui.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ResponseCartProductDto>> getProducts(@AuthenticationPrincipal final AuthInfo authInfo) {
        final Cart cart = cartService.findCartProductsByMember(authInfo);
        final List<ResponseCartProductDto> cartProductDtos = cart.getCartProducts()
                .stream()
                .map(ResponseCartProductDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartProductDtos);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addProductToCart(@RequestBody @Valid final AddCartRequestDto addCartRequestDto, @AuthenticationPrincipal final AuthInfo authInfo) {
        cartService.addProductToCart(addCartRequestDto, authInfo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable final Long id, @AuthenticationPrincipal final AuthInfo authInfo) {
        cartService.deleteProductFromCart(id, authInfo);
        return ResponseEntity.ok().build();
    }
}
