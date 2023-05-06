package cart.service.product;

import cart.controller.dto.request.ProductCreationRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCreateService {
    private final ProductDao productDao;

    public ProductCreateService(@Qualifier("productJdbcDao") final ProductDao productDao) {
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
}
