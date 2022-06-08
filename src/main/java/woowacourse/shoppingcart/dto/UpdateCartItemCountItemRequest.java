package woowacourse.shoppingcart.dto;

public class UpdateCartItemCountItemRequest {

    private Integer count;

    public UpdateCartItemCountItemRequest(){}

    public UpdateCartItemCountItemRequest(final Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
