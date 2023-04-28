package cart.repository;

import static java.util.stream.Collectors.toList;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public long insert(final Product product) {
        ProductEntity productEntity = new ProductEntity(product.getName(), product.getPrice(), product.getImageUrl());

        return productDao.insert(productEntity);
    }

    public Product findById(final long id) {
        ProductEntity result = productDao.findById(id);

        return new Product(result.getName(), result.getPrice(), result.getImageUrl());
    }

    public List<Product> findAll() {
        List<ProductEntity> result = productDao.findAll();

        return result.stream()
                .map(productEntity -> new Product(productEntity.getName(), productEntity.getPrice(), productEntity.getImageUrl()))
                .collect(toList());
    }

    public void update(final long id, final Product newProduct) {
        ProductEntity newProductEntity = new ProductEntity(id, newProduct.getName(), newProduct.getPrice(), newProduct.getImageUrl());

        productDao.update(newProductEntity);
    }

    public void delete(final long id) {
        productDao.delete(id);
    }

    public boolean isExist(final long id) {
        return productDao.isExist(id);
    }
}
