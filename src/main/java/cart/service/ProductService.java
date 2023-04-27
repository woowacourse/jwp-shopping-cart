package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;

    public ProductService(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void add(final String name, final String image, final Long price) {
        final Product product = new Product(name, image, price);
        productDao.insert(toEntity(product));
    }

    public void delete(final Integer id) {
        productDao.deleteById(id);
    }

    public void update(final Integer id, final String name, final String image, final Long price) {
        final Product product = new Product(id, name, image, price);
        productDao.update(toEntity(product));
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productDao.findAll().stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private Product fromEntity(ProductEntity productEntity) {
        return new Product(productEntity.getId(),
                productEntity.getName(),
                productEntity.getImage(),
                productEntity.getPrice()
        );
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice()
        );
    }
}
