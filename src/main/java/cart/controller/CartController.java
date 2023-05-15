package cart.controller;

import cart.auth.Auth;
import cart.auth.AuthInfo;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.domain.Product;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart/products")
public class CartController {

    private final CartDao cartDao;

    public CartController(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(@PathVariable final Long productId, @Auth final AuthInfo authInfo) {
        cartDao.saveCartItemByMemberEmail(authInfo.getEmail(), productId);

        return ResponseEntity.created(URI.create("/cart/products" + productId)).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProductsByMember(@Auth final AuthInfo authInfo) {
        List<Product> cartItems = cartDao.findCartItemsByMemberEmail(authInfo.getEmail());
        List<ProductResponse> productResponses = cartItems.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        CartResponse cartResponse = new CartResponse(productResponses);

        return ResponseEntity.ok().body(cartResponse.getProductResponses());
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeProduct(@PathVariable final Long cartId) {
        cartDao.deleteCartItemById(cartId);

        return ResponseEntity.noContent().build();
    }
}
