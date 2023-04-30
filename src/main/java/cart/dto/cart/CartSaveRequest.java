package cart.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartSaveRequest {
    @JsonProperty("productId")
    private Long id;

    public CartSaveRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
