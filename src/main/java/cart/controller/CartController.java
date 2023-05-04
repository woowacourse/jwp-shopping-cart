package cart.controller;

import cart.auth.MemberInfo;
import cart.auth.Principal;
import cart.request.ProductDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Principal MemberInfo memberInfo,
            @Valid @RequestBody ProductDto productDto) {
        cartService.addProduct(memberInfo, productDto);
        return ResponseEntity.created(URI.create("/carts")).build();
    }
}
