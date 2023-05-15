package cart.domain.cart;

public class CartProduct {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartProduct(final Long memberId, final Long productId) {
        this(null, memberId, productId);
    }

    public CartProduct(final Long id, final Long memberId, final Long productId) {
        validateMemberId(memberId);
        validateProductId(productId);

        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    private void validateMemberId(final Long memberId) {
        validateIdNotNull(memberId, "맴버 ID는 필수입니다.");
    }

    private void validateProductId(final Long productId) {
        validateIdNotNull(productId, "상품 ID는 필수입니다.");
    }

    private void validateIdNotNull(final Long id, final String message) {
        if (id == null) {
            throw new IllegalArgumentException(message);
        }
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
