package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import cart.global.exception.ProductNotFoundException;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import cart.service.dto.ProductSearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
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

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> searchAllProducts() {
        List<ProductEntity> productEntities = productDao.findAll();

        return productEntities.stream()
                .map(entity -> new ProductSearchResponse(
                        entity.getId(),
                        entity.getName(),
                        entity.getPrice(),
                        entity.getImageUrl())
                )
                .collect(Collectors.toList());
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

        int affectedRowCount = productDao.update(modifiedProductEntity);
        if (affectedRowCount == 0) {
            throw new ProductNotFoundException();
        }
    }

    public void deleteProduct(final Long productId) {
        int affectedRowCount = productDao.deleteById(productId);
        if (affectedRowCount == 0) {
            throw new ProductNotFoundException();
        }
    }
}
