package cart.cart.controller;

import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.cart.usecase.DeleteOneProductInCartUseCase;
import cart.web.config.auth.AuthorizedUser;
import cart.web.dto.request.AuthorizedUserRequest;
import cart.web.dto.request.PathVariableId;
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
            @PathVariable final PathVariableId productId
    ) {
        final AuthorizedCartUserDto authorizedCartUserDto =
                new AuthorizedCartUserDto(request.getEmail(), request.getPassword());

        deleteOneProductInCartService.deleteSingleProductInCart(authorizedCartUserDto, productId.getId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
