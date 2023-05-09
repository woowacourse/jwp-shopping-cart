package cart.service.product;

import cart.dao.ProductDao;
import cart.domain.product.ProductEntity;
import cart.domain.product.ProductId;
import cart.dto.application.ProductEntityDto;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductFindService {

    private final ProductDao productDao;
    private final Function<ProductEntity, ProductEntityDto> productEntityToProductEntityDto =
            product -> new ProductEntityDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()
            );

    public ProductFindService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductEntityDto> findAll() {
        return productDao.findAll().stream()
                .map(productEntityToProductEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductEntityDto find(final long id) {
        final ProductId productId = new ProductId(id);
        validateExistData(productId);

        final ProductEntity result = productDao.find(productId);

        return new ProductEntityDto(
                result.getId(),
                result.getName(),
                result.getPrice(),
                result.getImageUrl()
        );
    }

    private void validateExistData(final ProductId productId) {
        if (!productDao.isExist(productId)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
