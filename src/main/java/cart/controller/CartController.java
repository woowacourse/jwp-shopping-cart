package cart.controller;

import cart.auth.BasicAuthorizationExtractor;
import cart.dto.CartDto;
import cart.entity.Member;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>> allCarts(HttpServletRequest request) {
        Member member = basicAuthorizationExtractor.extract(request);
        String email = member.getEmail();
        List<CartDto> carts = cartService.getCartsByEmail(email);

        return ResponseEntity.created(URI.create("/carts")).body(carts);
    }
}
