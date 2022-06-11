package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;

public class AddCartItemRequestDto {

    private final Long productId;
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private final Integer count;

    public AddCartItemRequestDto(final Long productId, final Integer count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }
}
