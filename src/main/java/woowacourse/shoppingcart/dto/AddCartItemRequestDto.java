package woowacourse.shoppingcart.dto;

public class AddCartItemRequestDto {

    private final Long productId;
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
