package cart.controller;

import java.net.URI;
import java.util.List;

import cart.dto.CartAddRequest;
import cart.dto.CartResponse;
import cart.dto.AuthMember;
import cart.mvcconfig.argumentresolver.AuthPrincipal;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/view-cart")
    public String cartPage() {
        return "/cart";
    }

    @GetMapping("/cart/products")
    public ResponseEntity<List<CartResponse>> findAll(@AuthPrincipal AuthMember authMember) {
        List<CartResponse> allProduct = cartService.findAllProductByMemberInfo(authMember);
        return ResponseEntity.ok(allProduct);
    }

    @PostMapping("/cart")
    public ResponseEntity<Void> add(@AuthPrincipal AuthMember authMember,
                                    @RequestBody CartAddRequest cartAddRequest) {
        long savedId = cartService.saveProduct(authMember, cartAddRequest);
        return ResponseEntity.created(URI.create("/cart/" + savedId)).build();
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable long cartId) {
        cartService.removeByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
}
