package cart.dto.cart;

import cart.dto.item.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private ItemResponse item;
    private Long count;
}
