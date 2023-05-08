package cart.controller;

import cart.auth.Login;
import cart.controller.dto.MemberRequest;
import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.service.CartService;
import cart.service.MemberService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final MemberService memberService;
    private final CartService cartService;

    public CartController(MemberService memberService, CartService cartService) {
        this.memberService = memberService;
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> loadCart(@Login MemberRequest member) {
        MemberResponse loginMember = memberService.login(member.getEmail(), member.getPassword());
        List<ProductResponse> products = cartService.findAllByMemberId(loginMember.getId());
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity addProduct(@PathVariable Long productId, @Login MemberRequest member) {
        MemberResponse loginMember = memberService.login(member.getEmail(), member.getPassword());
        cartService.add(loginMember.getId(), productId);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId, @Login MemberRequest member) {
        MemberResponse loginMember = memberService.login(member.getEmail(), member.getPassword());
        cartService.delete(loginMember.getId(), productId);
        return ResponseEntity.ok().build();
    }
}
