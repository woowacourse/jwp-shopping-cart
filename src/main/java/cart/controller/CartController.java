package cart.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.auth.Extractor;
import cart.dto.product.ProductResponse;
import cart.dto.user.UserRequest;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final Extractor extractor;

    public CartController(Extractor extractor) {
        this.extractor = extractor;
    }

    @GetMapping
    public String cart() {
        return "cart";
    }

    @GetMapping("/products")
    public List<ProductResponse> products(@RequestHeader HttpHeaders header) {
        final UserRequest userRequest = extractor.extractUser(header);
        return null;
    }
}
