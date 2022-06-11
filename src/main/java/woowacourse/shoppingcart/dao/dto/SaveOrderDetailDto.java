package woowacourse.shoppingcart.dao.dto;

public class SaveOrderDetailDto {

    private final long ordersId;
    private final long productId;
    private final int quantity;

    public SaveOrderDetailDto(long ordersId, long productId, int quantity) {
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getOrdersId() {
        return ordersId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
