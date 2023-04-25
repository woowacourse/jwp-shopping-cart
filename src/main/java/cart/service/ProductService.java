package cart.service;

import cart.domain.Product;
import cart.domain.Products;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final Products products;

    public ProductService(final Products products) {
        this.products = products;
    }

    public void add(final String name, final String image, final Long price) {
        final Product product = new Product(name, image, price);
        products.save(product);
    }

    public void delete(final Integer id) {
        products.delete(id);
    }

    public void modify(final Integer id, final String name, final String image, final Long price) {
        final Product product = new Product(id, name, image, price);
        products.modify(product);
    }

    public List<Product> getAll() {
        return products.findAll();
    }
}
