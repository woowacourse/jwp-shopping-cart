package cart.controller;

import cart.authority.Authority;
import cart.controller.dto.request.CartItemCreationRequest;
import cart.controller.dto.request.CartItemDeleteRequest;
import cart.controller.dto.request.MemberIdRequest;
import cart.domain.dto.CartDto;
import cart.domain.dto.ProductDto;
import cart.service.CartService;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart/items")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(final CartService cartService, final ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> addProduct(
            @RequestBody @Valid final CartItemCreationRequest productId,
            @Authority final MemberIdRequest memberId
    ) {
        cartService.addProduct(productId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findIdMemberId(@Authority final MemberIdRequest memberId) {
        final List<CartDto> cartDtos = cartService.getProducts(memberId);
        return ResponseEntity.ok(productService.findById(cartDtos));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(
            @RequestBody @Valid final CartItemDeleteRequest productId,
            @Authority final MemberIdRequest memberId
    ) {
        cartService.delete(memberId, productId);
        return ResponseEntity.noContent().build();
    }
}
