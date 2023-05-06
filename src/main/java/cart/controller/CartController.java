package cart.controller;

import cart.controller.auth.BasicTokenDecoder;
import cart.domain.product.Product;
import cart.service.CartService;
import cart.service.dto.UserAuthDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/me/products")
    public ResponseEntity<List<Product>> getProductsOfMyCart(HttpServletRequest request) {
        UserAuthDto userAuthDto = BasicTokenDecoder.extract(request);
        List<Product> products = this.cartService.findProductsInCartByUser(userAuthDto);
        return ResponseEntity.ok().body(products);
    }
}
