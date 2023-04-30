package cart.controller;

import cart.entity.Cart;
import cart.entity.Member;
import cart.repository.CartRepository;
import cart.repository.exception.CartPersistanceFailedException;
import cart.util.BasicExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class CartController {
    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostMapping("/cart")
    public ResponseEntity<Void> saveCart(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) throws CartPersistanceFailedException {
        Member member = BasicExtractor.extract(request);
        Long productId = Long.valueOf((Integer) requestBody.get("product_id"));
        cartRepository.save(new Cart(member.getEmail(), productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
