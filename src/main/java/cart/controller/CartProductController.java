package cart.controller;

import cart.auth.AuthenticationPrincipal;
import cart.auth.Credential;
import cart.dto.CartProductRequest;
import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import cart.service.CartProductService;
import cart.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart-products")
@RequiredArgsConstructor
public class CartProductController {
    private final CartProductService cartProductService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> save(@AuthenticationPrincipal final Credential credential, @RequestBody @Valid final CartProductRequest cartProductRequest) {
        final MemberResponse memberResponse = memberService.findByEmail(credential.getEmail());
        long id = cartProductService.save(memberResponse.getId(), cartProductRequest);
        return ResponseEntity.created(URI.create("/cart-products/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(@AuthenticationPrincipal final Credential credential) {
        final MemberResponse memberResponse = memberService.findByEmail(credential.getEmail());
        return ResponseEntity.ok(cartProductService.findAll(memberResponse.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal final Credential credential, @PathVariable final Long id) {
        final MemberResponse memberResponse = memberService.findByEmail(credential.getEmail());
        cartProductService.delete(memberResponse.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
