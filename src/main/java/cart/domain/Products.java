package cart.domain;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class Products {

    private final Set<Product> products; // 넣는 순서는 상관없으므로 동시성 문제가 없다.
    private final AtomicInteger sequence = new AtomicInteger(0); // 동시성 문제 해결

    public Products() {
        this(Set.of(
                new Product(1,"비버", "https://gmlwjd9405.github.io/images/network/rest.png", 10000L),
                new Product(2, "땡칠", "https://gmlwjd9405.github.io/images/network/rest.png", 5000L)
        ));
    }

    private Products(final Set<Product> products) {
        this.products = new HashSet<>(products);
        sequence.addAndGet(products.size());
    }

    public void save(final Product product) {
        Product productWithId = new Product(
                sequence.incrementAndGet(),
                product.getName(),
                product.getImage(),
                product.getPrice()
        );

        products.add(productWithId);
    }

    public Product findById(final Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
    }

    public void modify(final Product product) {
        products.add(product);
    }

    public void delete(final Integer id) {
        products.remove(findById(id));
    }

    public Set<Product> findAll() {
        return products;
    }
}
