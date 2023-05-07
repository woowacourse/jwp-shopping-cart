package shoppingbasket.cart.service;

import shoppingbasket.cart.dto.CartSelectResponseDto;
import shoppingbasket.cart.entity.CartProductEntity;

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
