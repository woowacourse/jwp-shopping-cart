package cart.controller;

import cart.controller.resolver.MemberId;
import cart.dto.response.CartProductResponse;
import cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart", description = "장바구니 API Document")
@RequestMapping("/carts")
@RestController
public final class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "장바구니 목록 조회 API", description = "사용자의 모든 장바구니 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CartProductResponse>> findCartItemsForMember(@MemberId Long memberId) {
        final List<CartProductResponse> response = cartService.findCartItemsForMember(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장바구니 상품 추가 API", description = "사용자의 장바구니에 상품을 추가한다.")
    @PostMapping
    public ResponseEntity<Void> putInCart(@RequestParam Long productId, @MemberId Long memberId) {
        final Long cartId = cartService.putInCart(productId, memberId);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @Operation(summary = "장바구니 상품 삭제 API", description = "사용자의 장바구니 상품을 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable(name = "id") Long cartId, @MemberId Long memberId) {
        cartService.removeCartItem(cartId, memberId);
        return ResponseEntity.noContent().build();
    }
}

