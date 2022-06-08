package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import woowacourse.shoppingcart.dto.response.ExistedProductResponse;

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

    @GetMapping("/{productId}")
    public ResponseEntity<ExistedProductResponse> hasProduct(@AuthenticationPrincipal
                                                                 final EmailAuthentication emailAuthentication,
                                                             @PathVariable final Long productId) {
        final boolean hasProduct = cartService.hasProduct(EmailDto.from(emailAuthentication), productId);
        return ResponseEntity.ok().body(new ExistedProductResponse(hasProduct));
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<Void> changeQuantity(@AuthenticationPrincipal final EmailAuthentication emailAuthentication,
                                               @RequestBody final CartRequest cartRequest) {
        cartService.updateQuantity(EmailDto.from(emailAuthentication), CartDto.from(cartRequest));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final EmailAuthentication emailAuthentication,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(EmailDto.from(emailAuthentication), cartId);
        return ResponseEntity.noContent().build();
    }
}
