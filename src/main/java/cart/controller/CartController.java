package cart.controller;

import cart.auth.AuthSubject;
import cart.cart.dto.CartResponse;
import cart.cart.service.CartService;
import cart.member.dto.MemberRequest;
import cart.product.domain.Product;
import cart.product.dto.ProductResponse;
import cart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findCartsByMemberRequest(@AuthSubject MemberRequest memberRequest) {
        List<CartResponse> cartResponses = cartService.findByMemberRequest(memberRequest);
        List<ProductResponse> products = productService.findByProductIds(getProductIds(cartResponses));
        return ResponseEntity.ok(products);
    }
    
    private List<Long> getProductIds(final List<CartResponse> cartResponses) {
        return cartResponses.stream()
                .map(CartResponse::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }
    
    @PostMapping("{productId}")
    public ResponseEntity<CartResponse> save(@PathVariable final Long productId, @AuthSubject MemberRequest memberRequest) {
        final Long cartId = cartService.addCart(productId, memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CartResponse(cartId, memberRequest.getId(), productId));
    }
}
