package cart.entity;

public class CartItemEntity {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartItemEntity(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartItemEntity of(final Long memberId, final Long productId){
        return new CartItemEntity(null, memberId, productId);
    }

    public static CartItemEntity of(final Long id, final Long memberId, final Long productId){
        return new CartItemEntity(id, memberId, productId);
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
