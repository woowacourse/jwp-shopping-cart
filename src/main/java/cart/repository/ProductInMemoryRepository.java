package cart.repository;

import cart.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductInMemoryRepository implements ProductRepository {

    List<Product> products = new ArrayList<>(List.of(Product.from(1L, "치킨", "url", 1000)));

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
        products.add(Product.from((long) (products.size() + 1), product.getName(), product.getImgUrl(), product.getPrice()));
    }

    @Override
    public void update(Product updateProduct) {
        int index = products.indexOf(updateProduct);
        products.set(index, updateProduct);
    }

    @Override
    public void deleteById(Long id) {
        products.removeIf(product -> product.isEqualsId(id));
    }
}
