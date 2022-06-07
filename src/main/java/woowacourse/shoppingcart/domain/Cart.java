package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final List<Product> products;
    private final List<Boolean> checks;
    private final List<Integer> quantities;

    public Cart(List<Product> products, List<Boolean> checks, List<Integer> quantities) {
        validateSizeEqual(products, checks, quantities);
        this.products = products;
        this.checks = checks;
        this.quantities = quantities;
    }

    private void validateSizeEqual(List<Product> products, List<Boolean> checked, List<Integer> quantities) {
        if (!(products.size() == checked.size() && checked.size() == quantities.size())) {
            throw new InvalidCartItemException("[ERROR] 제품의 수와 체크박스의 수는 같아야 합니다.");
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Boolean> getChecks() {
        return checks;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }
}
