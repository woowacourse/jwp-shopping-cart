package cart.controller;

import cart.config.infrastructure.AuthorizationExtractor;
import cart.config.infrastructure.BasicAuthorizationExtractor;
import cart.controller.dto.AuthRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.MemberResponse;
import cart.service.CartService;
import cart.service.MemberService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

    private static final AuthorizationExtractor<AuthRequest> basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    private final CartService cartService;
    private final MemberService memberService;

    public CartController(CartService cartService, MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> find(HttpServletRequest request) {
        AuthRequest authRequest = basicAuthorizationExtractor.extract(request);
        MemberResponse findMember = memberService.findByEmailAndPassword(authRequest.getEmail(),
                authRequest.getPassword());

        List<ItemResponse> response = cartService.findAllByMemberId(findMember.getId()).getItemResponses();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<CartResponse> addItem(@PathVariable Long itemId, HttpServletRequest request) {
        System.out.println("CartController.addItem");
        AuthRequest authRequest = basicAuthorizationExtractor.extract(request);
        MemberResponse findMember = memberService.findByEmailAndPassword(authRequest.getEmail(),
                authRequest.getPassword());

        CartResponse cartResponse = cartService.save(findMember.getId(), itemId);
        return ResponseEntity.created(URI.create("/carts/members/" + findMember.getId()))
                .body(cartResponse);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId, HttpServletRequest request) {
        System.out.println("CartController.deleteItem");
        AuthRequest authRequest = basicAuthorizationExtractor.extract(request);
        MemberResponse findMember = memberService.findByEmailAndPassword(authRequest.getEmail(),
                authRequest.getPassword());

        cartService.delete(findMember.getId(), itemId);
        return ResponseEntity.noContent().build();
    }
}
