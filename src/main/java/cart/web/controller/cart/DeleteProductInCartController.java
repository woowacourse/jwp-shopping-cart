package cart.web.controller.cart;

import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.cart.usecase.DeleteOneProductInCartUseCase;
import cart.web.config.auth.AuthorizedUser;
import cart.web.dto.request.AuthorizedUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController
public class DeleteProductInCartController {
    private final DeleteOneProductInCartUseCase deleteOneProductInCartService;

    public DeleteProductInCartController(final DeleteOneProductInCartUseCase deleteOneProductInCartService) {
        this.deleteOneProductInCartService = deleteOneProductInCartService;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductInCart(
            @AuthorizedUser final AuthorizedUserRequest request,
            @PathVariable final Long productId
    ) {
        final AuthorizedCartUserDto authorizedCartUserDto =
                new AuthorizedCartUserDto(request.getEmail(), request.getPassword());

        deleteOneProductInCartService.deleteSingleProductInCart(authorizedCartUserDto, productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
