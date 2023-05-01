package cart.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CartSaveRequest {

    @NotNull
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
