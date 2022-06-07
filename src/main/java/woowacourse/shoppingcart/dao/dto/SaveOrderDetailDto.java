package woowacourse.shoppingcart.dao.dto;

public class SaveOrderDetailDto {

    private final Long ordersId;
    private final Long productId;
    private final int quantity;

    public SaveOrderDetailDto(Long ordersId, Long productId, int quantity) {
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
