package cart.controller;

import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.entity.ProductEntity;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartDao mySQLCartDao;

    public CartController(CartDao mySQLCartDao) {
        this.mySQLCartDao = mySQLCartDao;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CartRequest cart) {
        mySQLCartDao.add(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        final List<ProductEntity> productEntities = mySQLCartDao.findByMeberId(1L);
        List<ProductResponse> cart = ProductResponse.from(productEntities);

        return ResponseEntity.ok().body(cart);

    }


}
