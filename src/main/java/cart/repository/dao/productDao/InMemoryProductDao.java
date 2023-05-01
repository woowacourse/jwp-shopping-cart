package cart.repository.dao.productDao;

import cart.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductDao implements ProductDao {

    private final List<Product> products = new LinkedList<>();

    @Override
    public Long save(final Product product) {
        final String name = product.getName();
        final String imageUrl = product.getImageUrl();
        final int price = product.getPrice();

        if (products.isEmpty()) {
            final Long id = 1L;
            products.add(new Product(id, name, imageUrl, price));
            return id;
        }

        final int lastIndex = products.size() - 1;
        final Long id = products.get(lastIndex).getId() + 1;
        products.add(new Product(id, name, imageUrl, price));
        return id;
    }

    @Override
    public Optional<Product> findById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public int update(final Product product) {
        final Long id = product.getId();
        final int index = removeProductByIdAndReturnIndex(id);
        products.add(index, product);
        return 1;
    }

    private int removeProductByIdAndReturnIndex(final Long id) {
        for (int i = 0; i < products.size(); i++) {
            final Product product = products.get(i);
            if (product.getId().equals(id)) {
                products.remove(i);
                return i;
            }
        }
        throw new IllegalArgumentException("해당 id가 존재하지 않습니다.");
    }

    @Override
    public int delete(final Long id) {
        removeProductByIdAndReturnIndex(id);
        return 1;
    }
}
