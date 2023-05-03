package cart.controller;

import cart.authority.Authority;
import cart.domain.dto.CartDto;
import cart.domain.dto.ProductDto;
import cart.service.CartService;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(final CartService cartService, final ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addProduct(
            @PathVariable("id") @NotNull(message = "상품 아이디가 비어있습니다.") final Long productId,
            @Authority final Long memberId
    ) {
        cartService.addProduct(productId, memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<ProductDto>> findIdMemberId(@Authority final Long memberId) {
        final List<CartDto> cartDtos = cartService.getProducts(memberId);
        return ResponseEntity.ok(productService.findById(cartDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("id") @NotNull(message = "상품 아이디가 비어있습니다.") final Long productId,
            @Authority final Long memberId
    ) {
        cartService.delete(memberId, productId);
        return ResponseEntity.ok().build();
    }
}
