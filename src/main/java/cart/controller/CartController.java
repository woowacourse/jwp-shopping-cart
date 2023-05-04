package cart.controller;

import cart.common.Member;
import cart.domain.cart.CartService;
import cart.domain.cart.dto.ProductResponse;
import cart.domain.member.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts/{productId}")
    @ResponseBody
    public ResponseEntity<Object> addCart(@Member MemberRequest memberRequest, @PathVariable Long productId) {
        Long cartId = cartService.addProductToCart(memberRequest.getEmail(), productId);
        return ResponseEntity.created(URI.create("carts/" + cartId)).build();
    }

    @GetMapping("/carts")
    public String showCart(@Member MemberRequest memberRequest) {
        return "cart";
    }

    @GetMapping("/carts/items")
    @ResponseBody
    public ResponseEntity<List<ProductResponse>> showCartItems(@Member MemberRequest memberRequest) {
        List<ProductResponse> cartItems = cartService.findProductsByUserIdOnCart(memberRequest.getEmail());
        return ResponseEntity.ok(cartItems);
    }

}
