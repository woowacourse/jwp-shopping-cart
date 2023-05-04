package cart.web.controller;

import cart.domain.cart.service.CartService;
import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.product.service.dto.ProductDto;
import cart.web.config.auth.AuthorizedUser;
import cart.web.controller.dto.request.AuthorizedUserRequest;
import cart.web.controller.dto.request.ProductInCartAdditionRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController
public class CartRestController {
    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProductInCart(
            @AuthorizedUser AuthorizedUserRequest request,
            @RequestBody ProductInCartAdditionRequest productId
    ) {
        AuthorizedCartUserDto authorizedCartUserDto =
                new AuthorizedCartUserDto(request.getEmail(), request.getPassword());

        cartService.addProductInCart(authorizedCartUserDto, productId.getProductId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProductsInCart(@AuthorizedUser AuthorizedUserRequest request) {
        AuthorizedCartUserDto authorizedCartUserDto =
                new AuthorizedCartUserDto(request.getEmail(), request.getPassword());

        List<ProductDto> allProductsInCart = cartService.findAllProductsInCart(authorizedCartUserDto);

        return ResponseEntity
                .ok(allProductsInCart);
    }
}
