package cart.dto;

import cart.entity.CartItemEntity;

public class CartItemDto {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartItemDto(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartItemDto of(final Long memberId, final Long productId){
        return new CartItemDto(null, memberId, productId);
    }

    public static CartItemDto of(final Long id, final Long memberId, final Long productId){
        return new CartItemDto(id, memberId, productId);
    }

    public static CartItemEntity toEntity(final CartItemDto cartItemDto){
        return CartItemEntity.of(cartItemDto.memberId, cartItemDto.productId);
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
