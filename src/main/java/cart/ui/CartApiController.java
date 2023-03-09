package cart.ui;

import cart.dao.CartItemDao;
import cart.dao.ProductDao;
import cart.domain.CartItem;
import cart.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartApiController {

    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartApiController(ProductDao productDao, CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> showProducts(Member member) {
        return ResponseEntity.ok(cartItemDao.findByMemberId(member.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(Member member, @RequestBody CartItemRequest cartItemRequest) {
        // 카트 아이템 저장
        Long cartItemId = cartItemDao.save(new CartItem(member, productDao.getProductById(cartItemRequest.getProductId())));

        return ResponseEntity.created(URI.create("/cart-items/" + cartItemId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(Member member, @PathVariable Long id) {
        // 아이템 조회
        CartItem cartItem = cartItemDao.findById(id);
        // 본인 확인
        cartItem.checkOwner(member);
        // 제거
        cartItemDao.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}