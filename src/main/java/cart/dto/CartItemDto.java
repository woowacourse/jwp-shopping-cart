package cart.dto;

import cart.entity.CartItemEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemDto {
    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public static CartItemDto from(CartItemEntity cartItemEntity) {
        return new CartItemDto(cartItemEntity.getId(),
                cartItemEntity.getName(),
                cartItemEntity.getPrice(),
                cartItemEntity.getImageUrl());
    }
}
