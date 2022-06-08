package woowacourse.shoppingcart.dto;

public class CartResponse {

    private Long id;
    private Integer quantity;

    public CartResponse() {
    }

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
