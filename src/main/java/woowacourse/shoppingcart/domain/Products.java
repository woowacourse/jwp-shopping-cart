package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Products {

    private final List<Product> products;

    public Products(final List<Product> products) {
        this.products = products;
    }

    public List<Product> slice(final int size, final int page) {
        final var fromIndex = size * (page - 1);
        final var toIndex = size * page;
        final var totalSize = products.size();

        if (toIndex > totalSize) {
            return new ArrayList<>(products.subList(fromIndex, totalSize));
        }
        return new ArrayList<>(products.subList(fromIndex, toIndex));
    }

    public Product findById(final Long productId) {
        return products.stream()
                .filter(it -> it.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않는 상품입니다."));
    }
}
