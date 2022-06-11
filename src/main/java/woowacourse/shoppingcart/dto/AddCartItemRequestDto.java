package woowacourse.shoppingcart.dto;

public class AddCartItemRequestDto {

    private Long productId;
    private Integer count;

    private AddCartItemRequestDto() {
    }

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
