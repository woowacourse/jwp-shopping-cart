package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Quantities {

    private final List<Quantity> quantities;

    public Quantities(List<Integer> quantities) {
        this.quantities = quantities.stream()
                .map(Quantity::new)
                .collect(Collectors.toList());
    }

    public int getQuantityAt(int index) {
        return quantities.get(index).getQuantity();
    }
    public int getSize() {
        return quantities.size();
    }
}
