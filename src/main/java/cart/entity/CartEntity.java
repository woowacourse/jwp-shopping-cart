package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CartEntity {

    public static final int SINGLE_QUANTITY = 1;
    private final ItemEntity item;
    private final Integer quantity;

    public CartEntity addQuantity() {
        return new CartEntity(item, quantity + SINGLE_QUANTITY);
    }

    public CartEntity deleteQuantity() {
        return new CartEntity(item, quantity - SINGLE_QUANTITY);
    }

    public boolean isSingleQuantity() {
        return quantity == SINGLE_QUANTITY;
    }

    public Long getItemId() {
        return item.getId();
    }
}
