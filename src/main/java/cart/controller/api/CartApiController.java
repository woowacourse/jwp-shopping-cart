package cart.controller.api;

import cart.annotation.BasicAuthorization;
import cart.argumentresolver.basicauthorization.BasicAuthInfo;
import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import cart.service.CartService;
import cart.service.CustomerService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public final class CartApiController {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartApiController(final CartService cartService, final CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<ProductEntity>> showCart(@BasicAuthorization BasicAuthInfo basicAuthInfo) {
        final Long customerId = customerService.findIdByEmailAndPassword(
            basicAuthInfo.getEmail(),
            basicAuthInfo.getPassword()
        );
        final List<ProductEntity> cartItems = cartService.findAllById(customerId);
        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addItem(
        @PathVariable(name = "productId") Long productId,
        @BasicAuthorization BasicAuthInfo basicAuthInfo
    ) {
        final Long customerId = customerService.findIdByEmailAndPassword(
            basicAuthInfo.getEmail(),
            basicAuthInfo.getPassword()
        );
        cartService.add(new CartEntity(customerId, productId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(
        @PathVariable(name = "productId") Long productId,
        @BasicAuthorization BasicAuthInfo basicAuthInfo
    ) {
        final Long customerId = customerService.findIdByEmailAndPassword(
            basicAuthInfo.getEmail(),
            basicAuthInfo.getPassword()
        );
        final Long cartId = cartService.findFirstIdBy(customerId, productId);
        cartService.delete(cartId);
        return ResponseEntity.noContent().build();
    }
}
