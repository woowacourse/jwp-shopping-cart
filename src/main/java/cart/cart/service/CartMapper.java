package cart.cart.service;

import cart.cart.dto.CartSelectResponseDto;
import cart.cart.entity.CartProductEntity;

public class CartMapper {

    public static CartSelectResponseDto toDto(final CartProductEntity cart) {
        return new CartSelectResponseDto(
                cart.getId(),
                cart.getProductName(),
                cart.getProductPrice(),
                cart.getProductImage()
        );
    }
}
