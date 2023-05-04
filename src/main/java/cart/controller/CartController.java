package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final MemberService memberService;

    public CartController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String getCarts() {
        return "cart";
    }

    @ResponseBody
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        List<ProductResponse> productByEmail = memberService.findProductByEmail(email);
        return ResponseEntity.ok().body(productByEmail);
    }


    @ResponseBody
    @PostMapping("/products/{productId}")
    public ResponseEntity<Void> addProduct(@PathVariable final Long productId, final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        memberService.save(email, productId);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        memberService.delete(email, productId);
        return ResponseEntity.ok().build();
    }
}
