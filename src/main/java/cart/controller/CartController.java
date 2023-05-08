package cart.controller;

import cart.config.auth.BasicTokenDecoder;
import cart.service.CartService;
import cart.service.dto.MemberAuthDto;
import cart.service.dto.ProductDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/me/products")
    public ResponseEntity<List<ProductDto>> getProductsOfMyCart(HttpServletRequest request) {
        MemberAuthDto memberAuthDto = BasicTokenDecoder.extract(request);
        List<ProductDto> products = this.cartService.findProductsInCartByUser(memberAuthDto);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/me/{productId}")
    public ResponseEntity<Void> addProductToMyCart(
            HttpServletRequest request,
            @NotNull @PathVariable Long productId
    ) {
        MemberAuthDto memberAuthDto = BasicTokenDecoder.extract(request);
        this.cartService.addProductToCartById(memberAuthDto, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/{productId}")
    public ResponseEntity<Void> deleteProductFromMyCart(
            HttpServletRequest request,
            @NotNull @PathVariable Long productId
    ) {
        MemberAuthDto memberAuthDto = BasicTokenDecoder.extract(request);
        this.cartService.deleteProductFromCartById(memberAuthDto, productId);
        return ResponseEntity.ok().build();
    }
}
