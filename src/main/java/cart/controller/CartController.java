package cart.controller;

import cart.controller.auth.LoginId;
import cart.controller.dto.AddCartRequest;
import cart.controller.dto.CartItemResponse;
import cart.domain.cart.Cart;
import cart.persistance.dao.CartDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
public class CartController {

    private final CartDao cartDao;

    public CartController(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @GetMapping("/cart-products")
    public ResponseEntity<List<CartItemResponse>> cartProducts(
            @LoginId final Long memberId
    ) {
        final Cart cart = cartDao.findByUserId(memberId);
        final List<CartItemResponse> response = CartItemResponse.createResponse(cart);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/cart-products")
    public ResponseEntity<Void> addProductToCart(
            @LoginId final Long memberId,
            @Valid @RequestBody final AddCartRequest addCartRequest
    ) {
        cartDao.addProduct(memberId, addCartRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart-products/{id}")
    public ResponseEntity<Void> removeProductFromCart(
            @Positive @PathVariable final Long id
    ) {
        cartDao.removeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
