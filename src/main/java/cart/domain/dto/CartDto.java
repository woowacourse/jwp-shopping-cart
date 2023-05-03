package cart.domain.dto;

public class CartDto {
    private final Long id;
    private final Long productId;
    private final Long memberId;

    public CartDto(final Long id, final Long productId, final Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
