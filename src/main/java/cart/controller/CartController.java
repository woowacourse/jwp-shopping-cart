package cart.controller;

import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
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
    public ResponseEntity<List<ProductResponse>> getProducts(HttpServletRequest request) {
        final List<ProductEntity> productEntities = mySQLCartDao.findByMeber(extractMember(request));
        List<ProductResponse> cart = ProductResponse.from(productEntities);

        return ResponseEntity.ok().body(cart);
    }

    public Member extractMember(HttpServletRequest request){
        String credentials = request.getHeader("authorization").substring("Basic ".length());
        String[] decoded = new String(Base64Utils.decode(credentials.getBytes())).split(":");
        if (decoded.length != 2) {
            throw new IllegalArgumentException();
        }
        return new Member(decoded[0], decoded[1]);
    }
}
