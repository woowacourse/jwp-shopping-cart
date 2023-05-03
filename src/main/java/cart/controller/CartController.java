package cart.controller;

import cart.controller.auth.BasicAuthentication;
import cart.controller.dto.AddCartRequest;
import cart.domain.user.Member;
import cart.persistance.dao.CartDao;
import cart.persistance.entity.CartProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping(value = "/cart-products")
    public ResponseEntity<Void> addProductToCart(
            @BasicAuthentication final Member member,
            @RequestBody final AddCartRequest addCartRequest
    ) {
        cartDao.addProduct(member.getId(), addCartRequest.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
