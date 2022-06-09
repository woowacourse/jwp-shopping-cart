package woowacourse.shoppingcart.dao.dto;

public class SaveCartDto {

    private final Long memberId;
    private final Long productId;
    private final int quantity;

    public SaveCartDto(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = 1;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
