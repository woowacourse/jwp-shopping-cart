package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CartItemAddRequest {

    @NotNull
    private Long id;
    @NotNull
    private Integer quantity;

    public CartItemAddRequest() {
    }

    public CartItemAddRequest(Long id, Integer quantity) {
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
