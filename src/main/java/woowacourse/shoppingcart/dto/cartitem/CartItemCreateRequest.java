package woowacourse.shoppingcart.dto.cartitem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CartItemCreateRequest {

    private long productId;
    private long count;

    public static CartItemCreateRequest from(long productId, long count) {
        return new CartItemCreateRequest(productId, count);
    }
}
