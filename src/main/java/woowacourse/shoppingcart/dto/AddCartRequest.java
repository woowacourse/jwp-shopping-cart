package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCartRequest {

    @JsonProperty("product_id")
    private Long productId;

    public AddCartRequest() {
    }

    public AddCartRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
