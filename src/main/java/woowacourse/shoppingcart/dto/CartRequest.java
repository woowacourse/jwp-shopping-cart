package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "product id를 입력하세요.")
    private Long productId;

    @Min(value = 1, message = "장바구니 물품 개수를 1개 이상 입력해주세요.")
    private int quantity;

    private CartRequest() {
    }

    public CartRequest(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
