package woowacourse.shoppingcart.dto;

public class CartRequest {

    private Long id;
    private Integer quantity;

    private CartRequest() {

    }

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
