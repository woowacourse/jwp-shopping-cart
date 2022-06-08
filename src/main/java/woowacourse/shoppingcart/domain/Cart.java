package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.*;
import java.util.stream.Collectors;

public class Cart {
    private final Map<Long, Product> cart;
    private final List<Long> productId;
    private final List<Boolean> checks;
    private final List<Integer> quantities;

    public Cart(Map<Long, Product> cart, List<Boolean> checks, List<Integer> quantities) {
        validateSizeEqual(cart, checks, quantities);
        this.cart = cart;
        this.productId = cart.values()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        this.checks = checks;
        this.quantities = quantities;
    }

    private void validateSizeEqual(Map<Long, Product> cart, List<Boolean> checked, List<Integer> quantities) {
        Set<Integer> totalSize = new HashSet<>();
        totalSize.add(cart.size());
        totalSize.add(quantities.size());
        totalSize.add(checked.size());
        if (totalSize.size() != 1) {
            throw new InvalidCartItemException("[ERROR] 제품의 수와 체크박스의 수는 같아야 합니다.");
        }
    }

    public Product pickOneProduct(Long cartId) {
        if (!cart.containsKey(cartId)) {
            throw new InvalidCartItemException("[ERROR] 대응되는 상품이 존재하지 않습니다.");
        }
        return cart.get(cartId);
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

    public List<Long> getProductId() {
        return productId;
    }
}
