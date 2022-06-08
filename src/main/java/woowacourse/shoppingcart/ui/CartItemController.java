package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.dto.EmailAuthentication;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.dto.CartDto;
import woowacourse.shoppingcart.application.dto.EmailDto;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;

@RestController
@RequestMapping("/api/customers/cart")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final EmailAuthentication emailAuthentication) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerEmail(EmailDto.from(emailAuthentication)));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final CartRequest cartRequest,
                                            @AuthenticationPrincipal final EmailAuthentication emailAuthentication) {
        final Long cartId = cartService.addCart(CartDto.from(cartRequest), EmailDto.from(emailAuthentication));
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final EmailAuthentication emailAuthentication,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(EmailDto.from(emailAuthentication), cartId);
        return ResponseEntity.noContent().build();
    }
}
