package cart.cart.service;

import cart.cart.dto.CartInsertResponseDto;
import cart.cart.dto.CartSelectResponseDto;
import cart.cart.entity.CartEntity;
import cart.cart.entity.CartProductEntity;

public class CartMapper {

    public static CartInsertResponseDto toDto(final CartEntity cart) {
        return new CartInsertResponseDto(cart.getId(), cart.getMemberId(), cart.getProductId());
    }

    public static CartSelectResponseDto toDto(final CartProductEntity cart) {
        return new CartSelectResponseDto(
                cart.getId(),
                cart.getProductName(),
                cart.getProductPrice(),
                cart.getProductImage()
        );
    }
}
