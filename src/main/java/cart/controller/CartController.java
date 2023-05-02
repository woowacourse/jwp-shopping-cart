package cart.controller;

import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<CartResponse>> findByMemberId(@PathVariable Long memberId) {
        List<CartResponse> cartResponse = cartService.findAllByMemberId(memberId);
        return ResponseEntity.ok().body(cartResponse);
    }

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody @Valid CartRequest cartRequest) {
        CartResponse cartResponse = cartService.create(cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponse> update(@RequestBody @Valid CartRequest cartRequest, @PathVariable Long id) {
        CartResponse updated = cartService.update(cartRequest, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        cartService.deleteById(id);
    }
}
