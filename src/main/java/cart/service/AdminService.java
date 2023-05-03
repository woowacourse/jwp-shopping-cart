package cart.service;

import cart.controller.dto.ProductCreationRequest;
import cart.controller.dto.ProductUpdateRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
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

    public void save(final ProductCreationRequest productCreationRequest) {
        final Product product = new Product(
                productCreationRequest.getName(),
                productCreationRequest.getImage(),
                productCreationRequest.getPrice()
        );

        final ProductEntity productEntity = new ProductEntity(product);

        productDao.insert(productEntity);
    }

    public void delete(final Long id) {
        productDao.deleteById(id);
    }

    public void update(final ProductUpdateRequest productUpdateRequest) {
        final Product product = new Product(
                productUpdateRequest.getId(),
                productUpdateRequest.getName(),
                productUpdateRequest.getImage(),
                productUpdateRequest.getPrice()
        );

        final ProductEntity productEntity = new ProductEntity(product);

        productDao.update(productEntity);
    }
}
