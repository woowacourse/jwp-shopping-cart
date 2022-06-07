package woowacourse.shoppingcart.dto;

public class OrderQuantityInfo {
    private Long productId;
    private int quantity;

    public OrderQuantityInfo() {
    }

    public OrderQuantityInfo(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
