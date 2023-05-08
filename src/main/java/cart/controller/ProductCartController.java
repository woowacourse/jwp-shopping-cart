package cart.controller;

import cart.auth.AuthMember;
import cart.auth.AuthPrincipal;
import cart.dto.CartRequest;
import cart.dto.CartsResponse;
import cart.dto.ProductCartResponse;
import cart.service.ProductCartService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class ProductCartController {

    private final ProductCartService productCartService;

    public ProductCartController(ProductCartService productCartService) {
        this.productCartService = productCartService;
    }

    @GetMapping
    public ResponseEntity<CartsResponse> findMyCart(@AuthPrincipal AuthMember authMember) {
        CartsResponse cartsResponse = productCartService.findAllMyProductCart(authMember);
        return ResponseEntity.ok(cartsResponse);
    }

    @PostMapping
    public ResponseEntity<ProductCartResponse> addMyCart(
            @RequestBody CartRequest cartRequest,
            @AuthPrincipal AuthMember authMember
    ) {
        ProductCartResponse response = productCartService.addCart(cartRequest.getProductId(), authMember);
        return ResponseEntity.created(URI.create("/carts/" + response.getId()))
                .body(response);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteMyCart(
            @PathVariable Long cartId,
            @AuthPrincipal AuthMember authMember
    ) {
        productCartService.deleteProductInMyCart(cartId, authMember);
        return ResponseEntity.noContent().build();
    }
}
