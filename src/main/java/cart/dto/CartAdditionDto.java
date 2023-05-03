package cart.dto;

import cart.entity.CartItemEntity;

public class CartAdditionDto {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartAdditionDto(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartAdditionDto of(final Long memberId, final Long productId) {
        return new CartAdditionDto(null, memberId, productId);
    }

    public static CartAdditionDto of(final Long id, final Long memberId, final Long productId) {
        return new CartAdditionDto(id, memberId, productId);
    }

    public static CartItemEntity toEntity(final CartAdditionDto cartAdditionDto) {
        return CartItemEntity.of(cartAdditionDto.memberId, cartAdditionDto.productId);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
