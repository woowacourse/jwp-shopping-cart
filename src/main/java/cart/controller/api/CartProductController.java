package cart.controller.api;

import cart.auth.AuthenticationPrincipal;
import cart.domain.member.Member;
import cart.dto.ProductResponse;
import cart.mapper.ProductResponseMapper;
import cart.service.CartProductService;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartProductController {

    private final ProductService productService;
    private final CartProductService cartProductService;

    public CartProductController(final ProductService productService,
                                 final CartProductService cartProductService) {
        this.productService = productService;
        this.cartProductService = cartProductService;
    }

    @PostMapping
    public ResponseEntity<Void> postCartProduct(@RequestBody final Long productId,
                                                @AuthenticationPrincipal final Member member) {
        cartProductService.addCartProduct(productId, member.getEmail(), member.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getCartProduct(@AuthenticationPrincipal final Member member) {
        final List<Long> productIds = cartProductService.findAllProductIds(member.getEmail(), member.getPassword());

        final List<ProductResponse> productResponses = ProductResponseMapper.from(productService.findByIds(productIds));

        return ResponseEntity.ok(productResponses);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable final Long productId,
                                                  @AuthenticationPrincipal final Member member) {
        cartProductService.delete(productId, member.getEmail(), member.getPassword());

        return ResponseEntity.noContent().build();
    }
}
