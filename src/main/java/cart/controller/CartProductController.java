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

import java.util.List;

@RestController
@RequestMapping("/cart-products")
@RequiredArgsConstructor
public class CartProductController {
    private final CartProductService cartProductService;
    private final MemberService memberService;

    @PostMapping
    public long save(@AuthenticationPrincipal Credential credential, @RequestBody CartProductRequest cartProductRequest) {
        MemberResponse memberResponse = memberService.findByEmail(credential.getEmail());
        System.out.println("save: memberid - " + memberResponse.getId());
        System.out.println("product id - " + cartProductRequest.getProductId());
        return cartProductService.save(memberResponse.getId(), cartProductRequest);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(@AuthenticationPrincipal Credential credential) {
        MemberResponse memberResponse = memberService.findByEmail(credential.getEmail());
        return ResponseEntity.ok(cartProductService.findAll(memberResponse.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Credential credential, @PathVariable Long id) {
        MemberResponse memberResponse = memberService.findByEmail(credential.getEmail());
        cartProductService.delete(memberResponse.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
