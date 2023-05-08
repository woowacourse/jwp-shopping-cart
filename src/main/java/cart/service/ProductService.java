package cart.service;

import cart.dao.product.ProductDao;
import cart.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public void add(final Product product) {
        productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findByIds(final List<Long> productIds) {
        final List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            products.add(productDao.findById(productId));
        }

        return products;
    }

    public void updateById(final Long id, final Product product) {
        productDao.updateById(id, product);
    }

    public void deleteById(final Long id) {
        productDao.deleteById(id);
    }
}
