package woowacourse.shoppingcart.dto;

public class CartProductInfoResponse {

    private Long id;
    private int quantity;

    public CartProductInfoResponse() {
    }

    public CartProductInfoResponse(Long id, int quantity) {
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
