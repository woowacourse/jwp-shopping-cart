package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCommandService {

    private final ProductDao productDao;

    public ProductCommandService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void registerProduct(final ProductRegisterRequest productRegisterRequest) {
        ProductEntity productEntity = new ProductEntity(
                productRegisterRequest.getName(),
                productRegisterRequest.getPrice(),
                productRegisterRequest.getImageUrl()
        );

        productDao.save(productEntity);
    }

    public void modifyProduct(final Long productId, final ProductModifyRequest productModifyRequest) {
        final Product modifiedProduct = Product.of(
                productModifyRequest.getName(),
                productModifyRequest.getPrice(),
                productModifyRequest.getImageUrl()
        );

        final ProductEntity modifiedProductEntity =
                new ProductEntity(
                        productId,
                        modifiedProduct.getName(),
                        modifiedProduct.getPrice(),
                        modifiedProduct.getImageUrl()
                );

        productDao.modify(modifiedProductEntity);
    }

    public void deleteProduct(final Long productId) {
        productDao.deleteById(productId);
    }
}
