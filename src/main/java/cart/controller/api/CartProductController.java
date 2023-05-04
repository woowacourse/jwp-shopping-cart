package cart.controller.api;

import cart.auth.AuthorizationExtractor;
import cart.auth.BasicAuthorizationExtractor;
import cart.domain.member.Member;
import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.service.CartProductService;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartProductController {

    private final AuthorizationExtractor<AuthInfo> basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    private final ProductService productService;
    private final CartProductService cartProductService;

    public CartProductController(final ProductService productService,
                                 final CartProductService cartProductService) {
        this.productService = productService;
        this.cartProductService = cartProductService;
    }

    @PostMapping
    public ResponseEntity<Void> postCartProduct(@RequestBody final Long productId,
                                                @RequestAttribute("member") final Member member) {
        cartProductService.addCartProduct(productId, member.getEmail(), member.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getCartProduct(@RequestAttribute("member") final Member member) {
        final List<Long> productIds = cartProductService.findAllProductIds(member.getEmail(), member.getPassword());

        return ResponseEntity.ok(productService.findByIds(productIds));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable final Long productId,
                                                  @RequestAttribute("member") final Member member) {
        cartProductService.delete(productId, member.getEmail(), member.getPassword());

        return ResponseEntity.noContent().build();
    }
}
