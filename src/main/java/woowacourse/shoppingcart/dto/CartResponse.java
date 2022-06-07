package woowacourse.shoppingcart.dto;

public class CartResponse {

    private final Long id;
    private final Integer quantity;

    public CartResponse(Long id, Integer quantity) {
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
