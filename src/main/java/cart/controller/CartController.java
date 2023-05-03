package cart.controller;

import cart.dto.ProductDto;
import cart.service.CartService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllProductInCart(HttpServletRequest request) {
        int memberId = (int) request.getAttribute("memberId");

        List<ProductDto> allProduct = cartService.findAllProduct(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Object> addProductToCart(HttpServletRequest request, @PathVariable int productId) {
        int memberId = (int) request.getAttribute("memberId");

        cartService.addProduct(memberId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> removeProductFromCart(HttpServletRequest request, @PathVariable int productId) {
        int memberId = (int) request.getAttribute("memberId");

        cartService.deleteProduct(memberId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
