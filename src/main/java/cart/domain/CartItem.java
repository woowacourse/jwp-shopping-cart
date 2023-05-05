package cart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItem {

    private final Long id;
    private final String productName;
    private final String productImageUrl;
    private final String productPrice;
}
