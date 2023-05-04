package cart.controller;

import cart.auth.MemberInfo;
import cart.auth.Principal;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
            @Valid @RequestBody ProductRequestDto productRequestDto) {
        cartService.addProduct(memberInfo, productRequestDto);
        return ResponseEntity.created(URI.create("/carts")).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @Principal MemberInfo memberInfo,
            @PathVariable Integer productId) {
        cartService.deleteProduct(memberInfo, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductDto>> getProduct(@Principal MemberInfo memberInfo) {
        final List<ProductDto> productsOf = cartService.getProductsOf(memberInfo);
        return ResponseEntity.ok(productsOf);
    }
}
