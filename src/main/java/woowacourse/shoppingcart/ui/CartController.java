package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dao.CartQueryDao;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.cartitem.CartResponse;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartQueryDao cartQueryDao;

    @PostMapping
    public ResponseEntity<Void> addCartItem(
            @PathVariable final long customerId,
            @RequestBody final CartItemCreateRequest requestBody
    ) {
        final Long cartId = cartService.addCartItem(customerId, requestBody.getProductId(), requestBody.getCount());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> getCartItems(@PathVariable final long customerId) {
        return cartQueryDao.findAllCartByCustomerId(customerId);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItem(@PathVariable final long customerId,
                               @RequestParam final int productId,
                               @RequestBody final CartItemUpdateRequest request) {
        cartService.changeCartItemCount(customerId, productId, request.getCount());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable final long customerId,
                               @RequestParam final long productId) {
        cartService.deleteCart(customerId, productId);
    }
}
