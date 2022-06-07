package woowacourse.shoppingcart.dto;

public class CartRequest {

    private final Long id;
    private final Integer quantity;

    public CartRequest(Long id, Integer quantity) {
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
