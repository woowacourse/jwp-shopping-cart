package cart.controller;

import cart.auth.BasicAuthorizationExtractor;
import cart.dto.CartProductDto;
import cart.dto.CartRequestDto;
import cart.entity.Member;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartProductDto>> allCarts(HttpServletRequest request) {
        Member member = basicAuthorizationExtractor.extract(request);
        String email = member.getEmail();
        List<CartProductDto> carts = cartService.getCartsByEmail(email);
        return ResponseEntity.ok().body(carts);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addCart(HttpServletRequest request, @RequestBody CartRequestDto cartRequestDto) {
        Member member = basicAuthorizationExtractor.extract(request);
        String email = member.getEmail();
        int cartId = cartService.addCart(cartRequestDto.getProductId(), email);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }
}
