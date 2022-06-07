package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart {
    private final Map<Long, Product> cart;
    private final List<Boolean> checks;
    private final List<Integer> quantities;

    public Cart(Map<Long, Product> cart, List<Boolean> checks, List<Integer> quantities) {
        validateSizeEqual(cart, checks, quantities);
        this.cart = cart;
        this.checks = checks;
        this.quantities = quantities;
    }

    private void validateSizeEqual(Map<Long, Product> cart, List<Boolean> checked, List<Integer> quantities) {
        if (!(cart.size() == checked.size() && checked.size() == quantities.size())) {
            throw new InvalidCartItemException("[ERROR] 제품의 수와 체크박스의 수는 같아야 합니다.");
        }
    }

    public List<Long> getIds() {
        return new ArrayList<>(cart.keySet());
    }

    public List<Product> getProducts() {
        return new ArrayList<>(cart.values());
    }

    public List<Boolean> getChecks() {
        return checks;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }
}
