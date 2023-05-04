package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import org.apache.tomcat.util.codec.binary.Base64;
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
        String header = request.getHeader("Authorization");
        if (header == null) {
            System.out.println("no auth");
            return ResponseEntity.badRequest().build();
        }
        String[] strings = header.split(" ");
        byte[] bytes = Base64.decodeBase64(strings[1]);
        String auth = new String(bytes);
        String email = auth.split(":")[0];
        System.out.println(email);
        List<ProductResponse> productByEmail = memberService.findProductByEmail(email);
        return ResponseEntity.ok().body(productByEmail);
    }
}
