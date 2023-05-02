package cart.cart.service;

import cart.cart.dto.CartInsertResponseDto;
import cart.cart.entity.CartEntity;

public class CartMapper {

    public static CartInsertResponseDto toDto(final CartEntity cart) {
        return new CartInsertResponseDto(cart.getId(), cart.getMemberId(), cart.getProductId());
    }
}
