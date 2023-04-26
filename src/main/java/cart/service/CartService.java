package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dto.ProductResponse;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public long create(final String name, final int price, final String imageUrl) {
        final ProductEntity productEntity = new ProductEntity(name, price, imageUrl);
        return productDao.save(productEntity);
    }

    public List<ProductResponse> read() {
        final List<ProductEntity> products = productDao.findAll();
        return products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    public void update(final long id, final String name, final int price, final String imageUrl) {
        final ProductEntity productEntity = new ProductEntity(name, price, imageUrl);
        productDao.update(id, productEntity);
    }

    public void delete(final long id) {
        productDao.deleteById(id);
    }
}
