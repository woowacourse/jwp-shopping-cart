package woowacourse.shoppingcart.dto;

public class CartProductInfoRequest {

    private Long id;
    private int quantity;

    public CartProductInfoRequest() {
    }

    public CartProductInfoRequest(Long id, int quantity) {
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
