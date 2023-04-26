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
        validateExistData(id);

        final Product newProduct = new Product(id, name, price, imageUrl);

        productDao.update(newProduct);
    }

    public void deleteProduct(final long id) {
        validateExistData(id);
        
        productDao.delete(id);
    }

    private void validateExistData(final Long id) {
        if (!productDao.isExist(id)) {
            throw new IllegalArgumentException("존재하지 않는 id입니다.");
        }
    }
}
