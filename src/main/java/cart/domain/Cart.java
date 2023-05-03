package cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final Long id;
    private final Long userId;
    private final List<Product> products;

    public Cart(Long userId) {
        this(null, userId, Collections.emptyList());
    }

    // TODO: 2023-05-02 프레임워크에 의존하지 않고 products nonNUll검사할 방법?
    public Cart(Long id, Long userId, List<Product> products) {
        if (products.stream().anyMatch(Product::isIdNull)) {
            // TODO: 2023-05-02 예외처리
        }
        this.id = id;
        this.userId = userId;
        this.products = new ArrayList<>(products);
    }

    public void add(Product product) {
        if (product.isIdNull()) {
            // TODO: 2023-05-02 예외처리
        } 
        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    public void remove(Product product) {
        products.remove(product);
    }
}
