package cart.presentation;

import cart.business.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final CartService cartService;

    public MemberController(CartService productService) {
        this.cartService = productService;
    }

    @DeleteMapping(path = "/settings/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cartService.deleteMember(id));
    }
}
