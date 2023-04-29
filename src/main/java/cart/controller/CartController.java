package cart.controller;

import java.net.URI;
import java.util.List;

import cart.dto.CartResponse;
import cart.dto.AuthMember;
import cart.mvcconfig.argumentresolver.AuthPrincipal;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/cart/{productId}")
    public ResponseEntity<Void> add(@AuthPrincipal AuthMember authMember,
                    @PathVariable long productId) {
        long savedId = cartService.saveProduct(authMember, productId);
        return ResponseEntity.created(URI.create("/cart/" + savedId)).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<Void> delete(@AuthPrincipal AuthMember authMember,
                       @PathVariable long productId) {
        cartService.removeProductByMemberInfoAndProductId(authMember, productId);
        return ResponseEntity.noContent().build();
    }
}
