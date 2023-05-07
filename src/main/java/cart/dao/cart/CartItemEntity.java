package cart.dao.cart;

public class CartItemEntity {

    private static final Integer DEFAULT_AMOUNT = 1;

    private final Long memberId;
    private final Long productId;
    private final Integer amount;

    public CartItemEntity(Long memberId, Long productId, Integer amount) {
        this.memberId = memberId;
        this.productId = productId;
        this.amount = amount;
    }

    public CartItemEntity(Long memberId, Long productId) {
        this(memberId, productId, DEFAULT_AMOUNT);
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getAmount() {
        return amount;
    }
}
