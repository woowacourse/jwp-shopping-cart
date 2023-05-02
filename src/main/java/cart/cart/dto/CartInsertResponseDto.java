package cart.cart.dto;

public class CartInsertResponseDto {
    private final int id;
    private final int memberId;
    private final int productId;

    public CartInsertResponseDto(final int id, final int memberId, final int productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getProductId() {
        return productId;
    }
}
