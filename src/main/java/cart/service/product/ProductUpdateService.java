package cart.service.product;

import cart.controller.dto.request.ProductUpdateRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductUpdateService {

    private final ProductDao productDao;

    public ProductUpdateService(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
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
