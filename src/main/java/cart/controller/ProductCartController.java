package cart.controller;

import cart.auth.AuthPrincipal;
import cart.dto.CartRequest;
import cart.dto.CartsResponse;
import cart.dto.ProductCartResponse;
import cart.entity.Member;
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
    public ResponseEntity<CartsResponse> findMyCart(@AuthPrincipal Member member) {
        CartsResponse cartsResponse = productCartService.findAllMyProductCart(member);
        return ResponseEntity.ok(cartsResponse);
    }

    @PostMapping
    public ResponseEntity<ProductCartResponse> addMyCart(
            @RequestBody CartRequest cartRequest,
            @AuthPrincipal Member member
    ) {
        ProductCartResponse response = productCartService.addCart(cartRequest.getProductId(), member);
        return ResponseEntity.created(URI.create("/carts/" + response.getId()))
                .body(response);
    }

    @DeleteMapping("/{cartId다}")
    public ResponseEntity<Void> deleteMyCart(
            @PathVariable Long cartId,
            @AuthPrincipal Member member
    ) {
        productCartService.deleteProductInMyCart(cartId, member);
        return ResponseEntity.noContent().build();
    }
}
