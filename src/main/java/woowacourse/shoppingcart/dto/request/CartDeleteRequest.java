package woowacourse.shoppingcart.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;

public class CartDeleteRequest {

    @NotNull(message = "삭제할 카트id는 필수 항목입니다.")
    private List<Long> cartIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(List<Long> cartIds) {
        this.cartIds = cartIds;
    }

    public List<Long> getCartIds() {
        return cartIds;
    }
}
