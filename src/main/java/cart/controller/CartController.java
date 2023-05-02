package cart.controller;

import cart.auth.AuthSubject;
import cart.cart.dto.CartProductResponse;
import cart.cart.dto.CartResponse;
import cart.cart.service.CartService;
import cart.member.dto.MemberRequest;
import cart.product.dto.ProductResponse;
import cart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<CartProductResponse>> findCartsByMemberRequest(@AuthSubject MemberRequest memberRequest) {
        List<CartResponse> cartResponses = cartService.findByMemberRequest(memberRequest);
        List<ProductResponse> products = productService.findByProductIds(getProductIds(cartResponses));
        return ResponseEntity.ok(parseCartProductResponses(cartResponses, products));
    }
    
    private List<Long> getProductIds(final List<CartResponse> cartResponses) {
        return cartResponses.stream()
                .map(CartResponse::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }
    
    private List<CartProductResponse> parseCartProductResponses(final List<CartResponse> cartResponses, final List<ProductResponse> products) {
        return IntStream.range(0, cartResponses.size())
                .mapToObj(cartCount -> CartProductResponse.from(cartResponses.get(cartCount).getId(), products.get(cartCount)))
                .collect(Collectors.toUnmodifiableList());
    }
    
    @PostMapping("{productId}")
    public ResponseEntity<CartResponse> save(@PathVariable final Long productId, @AuthSubject MemberRequest memberRequest) {
        final Long cartId = cartService.addCart(productId, memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CartResponse(cartId, memberRequest.getId(), productId));
    }
}
