package cart.dto;

import cart.entity.item.CartItem;

public class CartItemResponse {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartItemResponse(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartItemResponse from(final CartItem addedItem) {
        return new CartItemResponse(
                addedItem.getId(),
                addedItem.getMemberId(),
                addedItem.getProductId());
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
