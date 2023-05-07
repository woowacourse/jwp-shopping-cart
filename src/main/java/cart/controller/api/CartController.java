package cart.controller.api;

import cart.auth.AuthPrincipal;
import cart.dto.MemberDto;
import cart.dto.request.CreateCartRequest;
import cart.dto.request.DeleteCartRequest;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(@AuthPrincipal final MemberDto memberDto) {
        final List<CartResponse> productResponses = cartService.find(memberDto);
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addCart(@AuthPrincipal final MemberDto memberDto, @RequestBody @Valid CreateCartRequest createCartRequest) {
        final Long id = cartService.insert(createCartRequest.getProductId(), memberDto);
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }

    @DeleteMapping("/carts")
    public ResponseEntity<Void> removeCart(@AuthPrincipal final MemberDto memberDto, @RequestBody @Valid DeleteCartRequest deleteCartRequest) {
        cartService.delete(deleteCartRequest.getProductId(), memberDto);
        return ResponseEntity.accepted().build();
    }
}
