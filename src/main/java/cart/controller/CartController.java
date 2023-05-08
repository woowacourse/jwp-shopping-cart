package cart.controller;

import cart.auth.Auth;
import cart.domain.member.Member;
import cart.dto.CartProductDto;
import cart.dto.CartProductSaveRequest;
import cart.service.CartProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController()
public class CartController {

    private final CartProductService cartProductService;

    public CartController(final CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@Auth Member member, @RequestBody @Valid CartProductSaveRequest request) {
        final Long id = cartProductService.save(member, request);
        return ResponseEntity.created(URI.create("/cart/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<CartProductDto> findAll(@Auth final Member member) {
        final CartProductDto result = cartProductService.findAll(member);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Void> delete(
            @Auth final Member member,
            @PathVariable final Long productId
    ) {
        cartProductService.delete(productId, member);
        return ResponseEntity.noContent().build();
    }
}
