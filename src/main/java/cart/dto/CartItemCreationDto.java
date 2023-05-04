package cart.dto;

import cart.entity.CartItemEntity;

public class CartItemCreationDto {

    private final long memberId;
    private final long productId;

    private CartItemCreationDto(final long memberId, final long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartItemCreationDto of(final long memberId, final long productId) {
        return new CartItemCreationDto(memberId, productId);
    }

    public static CartItemEntity toEntity(final CartItemCreationDto cartItemCreationDto) {
        return CartItemEntity.of(cartItemCreationDto.memberId, cartItemCreationDto.productId);
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
