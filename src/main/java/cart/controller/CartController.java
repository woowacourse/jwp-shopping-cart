package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartController {

    private final MemberService memberService;

    public CartController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        List<ProductResponse> productByEmail = memberService.findProductByEmail(email);
        return ResponseEntity.ok().body(productByEmail);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(@PathVariable final Long productId, final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        memberService.save(email, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        memberService.delete(email, productId);
        return ResponseEntity.ok().build();
    }
}
