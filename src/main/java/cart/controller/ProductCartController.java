package cart.controller;

import cart.auth.AuthPrincipal;
import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ProductResponse;
import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import cart.service.ProductCartService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<CartResponse> findMyCart(@AuthPrincipal Member member) {
        List<Product> products = productCartService.findAllMyProductCart(member);
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CartResponse(productResponses));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addMyCart(
            @RequestBody CartRequest cartRequest,
            @AuthPrincipal Member member
    ) {
        ProductCart productCart = productCartService.addCart(cartRequest.getProductId(), member);
        return ResponseEntity.created(URI.create("/carts/" + productCart.getId())).build();
    }
}
