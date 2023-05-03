package cart.controller.api;

import cart.controller.util.BasicAuthExtractor;
import cart.entity.Product;
import cart.service.ShoppingCartService;
import cart.service.dto.MemberInfo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(final ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> shoppingList(final HttpServletRequest httpServletRequest) {
        final MemberInfo info = BasicAuthExtractor.extract(httpServletRequest);
        final List<Product> allProduct = shoppingCartService.findAllProduct(info);
        return ResponseEntity.ok().body(allProduct);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody final Long productId,
                                           final HttpServletRequest httpServletRequest) {
        final MemberInfo info = BasicAuthExtractor.extract(httpServletRequest);
        shoppingCartService.addCartProduct(info, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
