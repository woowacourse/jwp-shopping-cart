package cart.repository;

import cart.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductInMemoryRepository implements ProductRepository {

    List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.isEqualsId(id))
                .findAny();
    }

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public void update(Product updateProduct) {
        int index = products.indexOf(updateProduct);
        products.set(index, updateProduct);
    }
}
