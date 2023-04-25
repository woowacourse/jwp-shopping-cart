package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(final String name, final int price, final String image) {
        Product product = new Product(name, price, image);
        productDao.insert(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }
}
