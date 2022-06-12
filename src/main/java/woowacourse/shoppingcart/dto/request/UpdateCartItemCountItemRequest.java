package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;

public class UpdateCartItemCountItemRequest {

    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer count;

    public UpdateCartItemCountItemRequest() {
    }

    public UpdateCartItemCountItemRequest(final Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
