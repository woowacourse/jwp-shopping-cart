package cart.controller;

import cart.controller.auth.BasicAuthentication;
import cart.domain.user.Member;
import cart.persistance.dao.CartDao;
import cart.persistance.entity.CartProductEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {

    private final CartDao cartDao;

    public CartController(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @GetMapping(value = "/cart-products")
    public ResponseEntity<List<CartProductEntity>> cartProducts(
            @BasicAuthentication final Member member
    ) {
        final List<CartProductEntity> cartProducts = cartDao.findByUserId(member.getId());
        return ResponseEntity.ok().body(cartProducts);
    }
}
