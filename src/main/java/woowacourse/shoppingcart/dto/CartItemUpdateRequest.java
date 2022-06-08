package woowacourse.shoppingcart.dto;

public class CartItemUpdateRequest {

    private Integer count;

    public CartItemUpdateRequest(Integer count) {
        this.count = count;
    }

    private CartItemUpdateRequest() {
    }

    public Integer getCount() {
        return count;
    }
}
