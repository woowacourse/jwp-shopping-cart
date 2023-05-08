package cart.controller;

import cart.dto.AuthInfo;
import cart.entity.ProductEntity;
import cart.service.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "사용자 장바구니 상품 조회")
    @GetMapping("/products")
    public ResponseEntity<List<ProductEntity>> getProductsOfCart(final AuthInfo authInfo) {
        final List<ProductEntity> products = cartService.showProductsBy(authInfo);

        return ResponseEntity.ok(products);
    }

    @ApiOperation(value = "장바구니에 상품 추가")
    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(final AuthInfo authInfo, @PathVariable long productId) {
        cartService.addToCart(authInfo, productId);
    }

    @ApiOperation(value = "장바구니 상품 제거")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(final AuthInfo authInfo, @PathVariable long productId) {
        cartService.deleteProductBy(authInfo, productId);
    }
}
