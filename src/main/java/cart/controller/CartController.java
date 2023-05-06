package cart.controller;

import cart.dto.CartProductAddRequest;
import cart.dto.CartProductRemoveRequest;
import cart.dto.ProductDto;
import cart.service.CartService;
import cart.webconfig.AuthMemberId;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> findAllProductInCart(@AuthMemberId int memberId) {
        List<ProductDto> allProduct = cartService.findAllProduct(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }

    @PostMapping()
    public ResponseEntity<Object> addProductToCart(@AuthMemberId int memberId,
                                                   @RequestBody CartProductAddRequest cartProductAddRequest) {
        cartService.addProduct(memberId, cartProductAddRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Object> removeProductFromCart(@AuthMemberId int memberId,
                                                        @RequestBody CartProductRemoveRequest cartProductRemoveRequest) {
        cartService.deleteProduct(memberId, cartProductRemoveRequest.getProductId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
