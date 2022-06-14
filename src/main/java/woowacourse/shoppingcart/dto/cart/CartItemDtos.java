package woowacourse.shoppingcart.dto.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartItemDtos {

    private final List<CartItemDto> values;

    public CartItemDtos() {
        this.values = new ArrayList<>();
    }

    public CartItemDtos(List<CartItemDto> values) {
        this.values = new ArrayList<>(values);
    }

    public void add(CartItemDto cartItemDto) {
        values.add(cartItemDto);
    }

    public List<CartItemDto> getValues() {
        return Collections.unmodifiableList(values);
    }
}
