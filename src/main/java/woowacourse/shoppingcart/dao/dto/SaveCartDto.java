package woowacourse.shoppingcart.dao.dto;

public class SaveCartDto {

    private final long memberId;
    private final long productId;
    private final int quantity;

    public SaveCartDto(long memberId, long productId) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = 1;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
