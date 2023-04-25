package cart.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Products {

    private final List<Product> products; // 넣는 순서는 상관없으므로 동시성 문제가 없다.
    private final AtomicInteger sequence = new AtomicInteger(0); // 동시성 문제 해결
    public Products(final List<Product> products) {
        this.products = products;
    }

    public void save(final Product product) {
        Product productWithId = new Product(sequence.incrementAndGet(), product);
        products.add(productWithId);
    }

    public Product findByName(final String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
    }

    public void delete(final String name) {
        products.remove(findByName(name));
    }

    public List<Product> getProducts() {
        return products;
    }
}
