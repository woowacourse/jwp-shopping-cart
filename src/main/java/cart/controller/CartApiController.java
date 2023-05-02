package cart.controller;

import cart.authorization.BasicAuthorizationParser;
import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import cart.service.CartService;
import cart.service.CustomerService;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartApiController {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartApiController(final CartService cartService, final CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<ProductEntity>> showCart(@RequestHeader("Authorization") String authorization) {
        final BasicAuthorizationParser basicAuthorizationParser = new BasicAuthorizationParser(authorization);
        final Long customerId = customerService.findIdByEmailAndPassword(
            basicAuthorizationParser.getEmail(),
            basicAuthorizationParser.getPassword()
        );
        final List<ProductEntity> cartItems = cartService.findAllById(customerId);
        cartItems.sort(Comparator.comparing(ProductEntity::getName).reversed());
        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("/cart/{productId}")
    public ResponseEntity<Void> addItem(
        @RequestHeader("Authorization") String authorization,
        @PathVariable(name = "productId") Long productId
    ) {
        final BasicAuthorizationParser basicAuthorizationParser = new BasicAuthorizationParser(authorization);
        final Long customerId = customerService.findIdByEmailAndPassword(
            basicAuthorizationParser.getEmail(),
            basicAuthorizationParser.getPassword()
        );
        cartService.add(new CartEntity(customerId, productId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<Void> deleteItem(
        @RequestHeader("Authorization") String authorization,
        @PathVariable(name = "productId") Long productId
    ) {
        final BasicAuthorizationParser basicAuthorizationParser = new BasicAuthorizationParser(authorization);
        final Long customerId = customerService.findIdByEmailAndPassword(
            basicAuthorizationParser.getEmail(),
            basicAuthorizationParser.getPassword()
        );
        final Long cartId = cartService.findFirstIdBy(customerId, productId);
        cartService.delete(cartId);
        return ResponseEntity.noContent().build();
    }
}
