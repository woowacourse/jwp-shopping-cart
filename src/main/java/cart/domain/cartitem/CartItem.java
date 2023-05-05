package cart.domain.cartitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItem {

    private final Long id;
    private final String productName;
    private final String productImageUrl;
    private final Integer productPrice;
}
