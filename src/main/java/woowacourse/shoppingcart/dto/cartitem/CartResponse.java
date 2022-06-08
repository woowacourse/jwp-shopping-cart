package woowacourse.shoppingcart.dto.cartitem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CartResponse {

    private long productId;
    private String thumbnailUrl;
    private String name;
    private int price;
    private long quantity;
    private long count;
}
