package woowacourse.shoppingcart.dto;

public class OrderRequest {

    private Long id;
    private int quantity;

    public OrderRequest() {
    }

    public OrderRequest(final Long id, final int quantity) {
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
