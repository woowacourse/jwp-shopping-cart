package cart.controller;

import cart.auth.Authorization;
import cart.dto.CartItemRequest;
import cart.dto.ProductResponse;
import cart.service.CartItemService;
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
@RequestMapping("/cart-product")
public class CartItemApiController {

    private final CartItemService cartItemService;

    public CartItemApiController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }


    //todo 질문: productId의 상품 존재 여부는 어디서 확인하나요?
    //todo 질문:  cartProduct의 로케이션을 /cart-product/{memberId}로 하고 싶은데 어떻게 해야하나요?
    @PostMapping
    public ResponseEntity<Void> addProduct(@Authorization Long memberId,
            @RequestBody CartItemRequest cartItemRequest) {
        cartItemService.addProduct(memberId, cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts(@Authorization Long memberId) {
        List<ProductResponse> allProducts = cartItemService.findAllProducts(memberId);
        return ResponseEntity.ok(allProducts);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeMember(@Authorization Long memberId,
            @RequestBody CartItemRequest cartItemRequest) {
        cartItemService.removeProduct(memberId, cartItemRequest);
        return ResponseEntity.noContent().build();
    }

}
