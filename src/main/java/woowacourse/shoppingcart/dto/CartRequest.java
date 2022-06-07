package woowacourse.shoppingcart.dto;

public class CartRequest {
    
    private Long id;
    private int quantity;

    public CartRequest() {
    }

    public CartRequest(Long id, int quantity) {
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
