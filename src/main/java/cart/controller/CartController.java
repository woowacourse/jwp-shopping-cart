package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        System.out.println(email);
        List<ProductResponse> productByEmail = memberService.findProductByEmail(email);
        return ResponseEntity.ok().body(productByEmail);
    }
}
