package cart.dto;

import cart.entity.CartItemEntity;

public class CartAdditionDto {

    private final long memberId;
    private final long productId;

    private CartAdditionDto(final long memberId, final long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartAdditionDto of(final long memberId, final long productId) {
        return new CartAdditionDto(memberId, productId);
    }

    public static CartItemEntity toEntity(final CartAdditionDto cartAdditionDto) {
        return CartItemEntity.of(cartAdditionDto.memberId, cartAdditionDto.productId);
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
