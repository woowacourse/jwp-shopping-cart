package cart.controller.api;

import cart.controller.util.MemberInfoPrincipal;
import cart.service.ShoppingCartService;
import cart.service.dto.CartResponse;
import cart.service.dto.MemberInfo;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart/products")
@RestController
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(final ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> shoppingList(@MemberInfoPrincipal final MemberInfo info) {
        final List<CartResponse> cartResponses = shoppingCartService.findAllProduct(info);
        return ResponseEntity.ok().body(cartResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody final Long productId,
                                           @MemberInfoPrincipal final MemberInfo info) {
        shoppingCartService.addCartProduct(info, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable final Long id) {
        shoppingCartService.removeProduct(id);
        return ResponseEntity.noContent().build();
    }
}
