package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.dao.CartRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(HttpServletRequest request) {
        List<ProductResponse> cart=cartRepository.getProducts(request);

        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/{product_id}")
    public long create(@PathVariable("product_id") Long productId,
        HttpServletRequest request) {
        return cartRepository.add(productId, request);
    }

    @DeleteMapping("/{product_id}")
    public int remove(@PathVariable("product_id") Long productId, HttpServletRequest request) {
        return cartRepository.remove(productId, request);
    }


}
