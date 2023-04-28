package cart.dto.cart;

public class CartDbResponseDto {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartDbResponseDto(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartDbResponseDto from(final Long id, final Long memberId, final Long productId) {
        return new CartDbResponseDto(id, memberId, productId);
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
