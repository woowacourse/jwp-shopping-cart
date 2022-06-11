package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Positive;

public class ProductChangeRequest {

    @Positive(message = "[ERROR] 수량은 양수입니다.")
    private int quantity;

    public ProductChangeRequest() {
    }

    public ProductChangeRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
