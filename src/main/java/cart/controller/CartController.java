package cart.controller;

import cart.entity.Cart;
import cart.entity.Member;
import cart.entity.Product;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import cart.repository.exception.CartPersistanceFailedException;
import cart.util.BasicExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Product>> findByEmail(HttpServletRequest request) {
        Member member = BasicExtractor.extract(request);
        List<Cart> carts = cartRepository.findAllByMemberEmail(member.getEmail());

        List<Long> productIds = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productIds.stream()
                .map(productRepository::findById)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> saveCart(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) throws CartPersistanceFailedException {
        Member member = BasicExtractor.extract(request);
        Long productId = Long.valueOf((Integer) requestBody.get("product_id"));
        cartRepository.save(new Cart(member.getEmail(), productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
