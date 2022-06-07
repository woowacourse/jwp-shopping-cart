package woowacourse.shoppingcart.ui.dto;

public class OrderDetailRequest {

    private Long id;
    private int quantity;

    private OrderDetailRequest() {
    }

    public OrderDetailRequest(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
