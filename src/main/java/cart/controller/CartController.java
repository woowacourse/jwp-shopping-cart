package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.domain.Member;
import cart.service.CartService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(Member member) {
        List<ProductResponse> cart = ProductResponse.from(cartService.loadCartProducts(member));

        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/{product_id}")
    public long create(@PathVariable("product_id") Long productId, Member member) {
        return cartService.addCart(productId, member);
    }

    @DeleteMapping("/{product_id}")
    public int remove(@PathVariable("product_id") Long productId, Member member) {
        return cartService.removeCart(productId, member);
    }
}
