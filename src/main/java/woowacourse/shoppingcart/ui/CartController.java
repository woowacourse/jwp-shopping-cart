package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.domain.LoginMemberPrincipal;
import woowacourse.auth.ui.dto.LoginMember;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.service.CartService;
import woowacourse.shoppingcart.ui.dto.request.CartCreateRequest;
import woowacourse.shoppingcart.ui.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CartQuantityUpdateRequest;
import woowacourse.shoppingcart.ui.dto.response.CartResponse;

@RequestMapping("/api/customer/carts")
@RestController
public class CartController {
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@LoginMemberPrincipal LoginMember loginMember,
                                       @RequestBody CartCreateRequest cartCreateRequest) {
        cartService.add(loginMember.getId(), cartCreateRequest.getProductId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findByCartId(@LoginMemberPrincipal LoginMember loginMember) {
        final Carts carts = cartService.findCartsByCustomerId(loginMember.getId());

        final List<CartResponse> body = toCartResponses(carts);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    public ResponseEntity<Void> updateQuantity(@LoginMemberPrincipal LoginMember loginMember,
                                               @RequestBody CartQuantityUpdateRequest cartQuantityUpdateRequest) {
        cartService.updateQuantity(loginMember.getId(), cartQuantityUpdateRequest.getProductId(),
                cartQuantityUpdateRequest.getQuantity());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@LoginMemberPrincipal LoginMember loginMember,
                                           @RequestBody CartDeleteRequest cartDeleteRequest) {
        cartService.deleteByCartIds(loginMember.getId(), cartDeleteRequest.getCartIds());

        return ResponseEntity.noContent().build();
    }

    private List<CartResponse> toCartResponses(final Carts carts) {
        final List<Cart> cartsByCustomerId = carts.getCarts();

        return cartsByCustomerId.stream()
                .map(this::toCartResponse)
                .collect(Collectors.toList());
    }

    private CartResponse toCartResponse(Cart cart) {
        final Product product = cart.getProduct();

        return new CartResponse(cart.getId(), cart.getQuantity(), product.getId(), product.getName(),
                product.getPrice(),
                product.getImageUrl());
    }
}
