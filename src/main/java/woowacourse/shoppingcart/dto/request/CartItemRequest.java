package woowacourse.shoppingcart.dto.request;

public class CartItemRequest {

    private Long id;
    private Integer quantity;

    public CartItemRequest() {
    }

    public CartItemRequest(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
