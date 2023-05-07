package cart.controller.api;

import static java.util.stream.Collectors.toList;

import cart.auth.Auth;
import cart.controller.dto.CartProductRequest;
import cart.controller.dto.CartProductResponse;
import cart.dao.CartProductDao;
import cart.dao.ProductDao;
import cart.domain.CartProduct;
import cart.domain.Member;
import cart.domain.Product;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CartProductController {

    private final CartProductDao cartProductDao;
    private final ProductDao productDao;

    public CartProductController(final CartProductDao cartProductDao,
                                 final ProductDao productDao) {
        this.cartProductDao = cartProductDao;
        this.productDao = productDao;
    }

    @PostMapping("/cart-products")
    ResponseEntity<Long> addCartProduct(
            @Auth final Member member,
            @Valid @RequestBody final CartProductRequest request
    ) {
        final Long id = cartProductDao.save(
                new CartProduct(member.getId(), request.getProductId())
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/cart-products/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(id);
    }

    @DeleteMapping("/cart-products/{id}")
    ResponseEntity<Void> deleteCartProduct(
            @PathVariable("id") final Long id
    ) {
        cartProductDao.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart-products")
    ResponseEntity<List<CartProductResponse>> findAllByMemberId(
            @Auth final Member member
    ) {
        final List<CartProductResponse> responses =
                cartProductDao.findAllByMemberId(member.getId())
                        .stream()
                        .map(it -> CartProductResponse.of(it, getProduct(it)))
                        .collect(toList());
        return ResponseEntity.ok(responses);
    }

    private Product getProduct(final CartProduct cartProduct) {
        return productDao.findById(cartProduct.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 상품이 존재하지 않습니다."));
    }
}
