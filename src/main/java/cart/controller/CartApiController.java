package cart.controller;

import cart.annotation.Auth;
import cart.dto.request.CartProductRequest;
import cart.dto.request.Credential;
import cart.dto.response.CartProductResponse;
import cart.service.CartProductService;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartProductService cartProductService;

    public CartApiController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @GetMapping
    public ResponseEntity<List<CartProductResponse>> findCartProductsByMember(@Auth Credential credential) {
        List<CartProductResponse> cartProducts = cartProductService.findAllByMemberId(credential.getMemberId());
        return ResponseEntity.status(HttpStatus.OK).body(cartProducts);
    }

    @PostMapping
    public ResponseEntity<Void> addCartProduct(@Auth Credential credential,
                                               @RequestBody CartProductRequest cartProductRequest) {
        cartProductService.save(credential.getMemberId(), cartProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/product/" + cartProductRequest.getProductId()))
                .build();
    }

    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<Void> deleteCartProduct(@Auth Credential credential,
                                                  @PathVariable Long cartProductId) {
        cartProductService.deleteById(cartProductId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
