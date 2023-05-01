package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.dao.CartRepository;
import cart.domain.Member;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(HttpServletRequest request) {
        Member member = extractMember(request);
        List<ProductResponse> cart = cartRepository.getProducts(member);

        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/{product_id}")
    public long create(@PathVariable("product_id") Long productId,
        HttpServletRequest request) {
        Member member = extractMember(request);
        return cartRepository.add(productId, member);
    }

    @DeleteMapping("/{product_id}")
    public int remove(@PathVariable("product_id") Long productId, HttpServletRequest request) {
        Member member = extractMember(request);
        return cartRepository.remove(productId, member);
    }

    public Member extractMember(HttpServletRequest request) {
        String credentials = request.getHeader("authorization").substring("Basic ".length());
        String[] decoded = new String(Base64Utils.decode(credentials.getBytes())).split(":");
        if (decoded.length != 2) {
            throw new IllegalArgumentException("credential의 형식이 잘못되었습니다.");
        }
        return new Member(decoded[0], decoded[1]);
    }
}
