package cart.controller;

import cart.auth.Login;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.CartProductsResponse;
import cart.service.CartService;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Void> addProduct(
            @Login Member member,
            @PathVariable @NotNull Long productId
    ) {
        cartService.addProduct(member, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TODO: web / api 컨트롤러 분리
    @GetMapping
    public String renderCartPage() {
        return "cart";
    }

    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<CartProductsResponse> getCartProducts(@Login Member member) {
        List<Product> products = cartService.findCartProducts(member);
        return ResponseEntity.ok(CartProductsResponse.of(products));
    }
}
