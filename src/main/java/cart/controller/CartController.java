package cart.controller;

import cart.controller.auth.BasicAuthentication;
import cart.controller.dto.AddCartRequest;
import cart.persistance.dao.CartDao;
import cart.persistance.entity.CartProductEntity;
import cart.persistance.entity.user.Member;
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
    public ResponseEntity<List<CartProductEntity>> cartProducts(
            @BasicAuthentication final Member member
    ) {
        final List<CartProductEntity> cartProducts = cartDao.findByUserId(member.getId());
        return ResponseEntity.ok().body(cartProducts);
    }

    @PostMapping("/cart-products")
    public ResponseEntity<Void> addProductToCart(
            @BasicAuthentication final Member member,
            @Valid @RequestBody final AddCartRequest addCartRequest
    ) {
        cartDao.addProduct(member.getId(), addCartRequest.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart-products/{id}")
    public ResponseEntity<Void> removeProductFromCart(
            @Positive @PathVariable final Long id,
            @Valid @BasicAuthentication final Member member
    ) {
        cartDao.removeFromCartById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
