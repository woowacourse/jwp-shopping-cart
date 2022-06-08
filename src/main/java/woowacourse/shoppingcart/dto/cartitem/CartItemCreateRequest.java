package woowacourse.shoppingcart.dto.cartitem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
@Getter
public class CartItemCreateRequest {

    private long productId;
    private long count;
}
