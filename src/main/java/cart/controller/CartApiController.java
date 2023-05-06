package cart.controller;

import cart.service.CartService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;

    // TODO: 2023/05/06 productId 검증, member 검증, member 조회
    @PostMapping("/{productId}")
    public ResponseEntity<Void> add(@PathVariable long productId) {
        long memberId = 1L;
        long cartId = cartService.add(memberId, productId);
        return ResponseEntity
                .created(URI.create("/cart/" + cartId))
                .build();
    }

    // TODO: 2023/05/06 member 검증(권한), cartId 검증
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable long cartId) {
        cartService.delete(cartId);

        return ResponseEntity
                .ok()
                .build();
    }
}
