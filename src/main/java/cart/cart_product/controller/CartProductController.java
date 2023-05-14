package cart.cart_product.controller;

import cart.auth.AuthenticationPrincipal;
import cart.cart_product.service.CartProductService;
import cart.member.domain.Member;
import cart.product.domain.Product;
import cart.product.dto.ProductResponse;
import cart.product.mapper.ProductResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/products")
public class CartProductController {

    private final CartProductService cartProductService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> postCartProduct(@PathVariable final Long productId,
                                                @AuthenticationPrincipal final Member member) {
        cartProductService.add(productId, member.getEmail(), member.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getCartProduct(@AuthenticationPrincipal final Member member) {
        final List<Product> products = cartProductService.findAll(member.getEmail(), member.getPassword());

        return ResponseEntity.ok(ProductResponseMapper.from(products));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable final Long productId,
                                                  @AuthenticationPrincipal final Member member) {
        cartProductService.delete(productId, member.getEmail(), member.getPassword());

        return ResponseEntity.noContent().build();
    }
}
