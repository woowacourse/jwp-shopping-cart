package cart.web.controller.cart;

import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.cart.usecase.FindAllProductsInCartUseCase;
import cart.domain.product.service.dto.ProductResponseDto;
import cart.web.config.auth.AuthorizedUser;
import cart.web.dto.request.AuthorizedUserRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController
public class FindAllProductInCartController {
    private final FindAllProductsInCartUseCase findAllInCartService;

    public FindAllProductInCartController(final FindAllProductsInCartUseCase findAllInCartService) {
        this.findAllInCartService = findAllInCartService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProductsInCart(
            @AuthorizedUser final AuthorizedUserRequest request) {
        final AuthorizedCartUserDto authorizedCartUserDto =
                new AuthorizedCartUserDto(request.getEmail(), request.getPassword());

        final List<ProductResponseDto> allProductsInCart = findAllInCartService.findAllProductsInCart(
                authorizedCartUserDto);

        return ResponseEntity
                .ok(allProductsInCart);
    }
}
