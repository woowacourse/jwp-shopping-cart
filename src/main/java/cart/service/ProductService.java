package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public Long register(final String name, final int price, final String imageUrl) {
        final Product product = new Product(name, price, imageUrl);
        return productDao.insert(product);
    }

    public void updateProduct(final long id, final String name, final int price, final String imageUrl) {
        final Product newProduct = new Product(id, name, price, imageUrl);

        productDao.update(newProduct);
    }
}
