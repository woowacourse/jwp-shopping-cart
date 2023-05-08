package cart.controller;

import cart.auth.LoginUser;
import cart.auth.MemberInfo;
import cart.dto.response.ProductDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(
            @LoginUser MemberInfo memberInfo,
            @PathVariable Integer productId) {
        cartService.addProduct(memberInfo, productId);
        return ResponseEntity.created(URI.create("/carts")).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @LoginUser MemberInfo memberInfo,
            @PathVariable Integer productId) {
        cartService.deleteProduct(memberInfo, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductDto>> getProduct(@LoginUser MemberInfo memberInfo) {
        final List<ProductDto> productsOf = cartService.getProductsOf(memberInfo);
        return ResponseEntity.ok(productsOf);
    }
}
