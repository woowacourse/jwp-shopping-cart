package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductCreationDto;
import cart.dto.ProductUpdateDto;
import cart.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
    private final ProductDao productDao;

    public AdminService(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void add(final ProductCreationDto productCreationDto) {
        final Product product = Product.from(productCreationDto);

        final ProductEntity productEntity = new ProductEntity(product);

        productDao.insert(productEntity);
    }

    public void delete(final Integer id) {
        productDao.deleteById(id);
    }

    public void update(final ProductUpdateDto productUpdateDto) {
        final Product product = Product.from(productUpdateDto);

        final ProductEntity productEntity = new ProductEntity(product);

        productDao.update(productEntity);
    }
}
