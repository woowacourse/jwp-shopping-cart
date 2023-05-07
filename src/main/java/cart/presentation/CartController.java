package cart.presentation;

import cart.business.CartProductService;
import cart.business.CartService;
import cart.entity.Product;
import cart.presentation.dto.CartProductRequest;
import cart.presentation.dto.CartRequest;
import cart.presentation.dto.CartResponse;
import cart.presentation.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartController {

    private final CartProductService cartProductService;
    private final CartService cartService;

    public CartController(CartProductService cartProductService, CartService cartService) {
        this.cartProductService = cartProductService;
        this.cartService = cartService;
    }

    @PostMapping(path = "/cart")
    public ResponseEntity<Integer> createCart(@RequestBody CartRequest cartRequest) {
        return ResponseEntity.created(URI.create("/carts"))
                .body(cartService.create(cartRequest));
    }

    @PostMapping(path = "/cart/products/{id}")
    public ResponseEntity<Integer> insertProductInCart(@RequestBody CartProductRequest cartProductRequest) {
        return ResponseEntity.created(URI.create("/cart/product"))
                .body(cartProductService.create(cartProductRequest));
    }

    @GetMapping(path = "/cart/{id}")
    public ResponseEntity<CartResponse> readProductsByMemberId(@PathVariable Integer memberId) {
        CartResponse cartResponse = new CartResponse(memberId, memberId,
                cartService.findProductsByMemberId(memberId));

        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping(path = "/cart/products/{id}")
    public ResponseEntity<List<ProductResponse>> readProducts(@PathVariable Integer memberId) {
        List<Product> products = cartService.findProductsByMemberId(memberId);

        List<ProductResponse> response = products.stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/cart/product/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer productId) {
        return ResponseEntity.ok().body(cartProductService.delete(productId));
    }
}
