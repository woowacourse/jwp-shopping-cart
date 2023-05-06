package cart.web.controller.cart;

import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.cart.usecase.SaveOneProductInCartUseCase;
import cart.web.config.auth.AuthorizedUser;
import cart.web.dto.request.AuthorizedUserRequest;
import cart.web.dto.request.ProductInCartAdditionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart")
@RestController
public class SaveProductInCartController {
    private final SaveOneProductInCartUseCase saveOneProductInCartService;

    public SaveProductInCartController(final SaveOneProductInCartUseCase saveOneProductInCartService) {
        this.saveOneProductInCartService = saveOneProductInCartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProductInCart(
            @AuthorizedUser final AuthorizedUserRequest request,
            @RequestBody final ProductInCartAdditionRequest productId
    ) {
        final AuthorizedCartUserDto authorizedCartUserDto =
                new AuthorizedCartUserDto(request.getEmail(), request.getPassword());

        saveOneProductInCartService.addSingleProductInCart(authorizedCartUserDto, productId.getProductId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
