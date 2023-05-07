package cart.service.product;

import cart.dao.ProductDao;
import cart.domain.product.ProductEntity;
import cart.domain.product.ProductId;
import cart.dto.application.ProductEntityDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductUpdateService {

    private final ProductDao productDao;

    public ProductUpdateService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public ProductEntityDto updateProduct(final ProductEntityDto productDto) {
        validateExistData(new ProductId(productDto.getId()));

        final ProductEntity newProduct = new ProductEntity(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImageUrl()
        );

        productDao.update(newProduct);

        return productDto;
    }

    private void validateExistData(final ProductId productId) {
        if (!productDao.isExist(productId)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
