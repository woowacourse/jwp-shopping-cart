package woowacourse.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartItem {

    private long id;
    private long customerId;
    private long productId;
    private long count;
}
