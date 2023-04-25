package cart.service;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.request.ProductRequestDto;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final ProductCategoryDao productCategoryDao;

    public ProductService(final ProductDao productDao, final CategoryDao categoryDao,
        final ProductCategoryDao productCategoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.productCategoryDao = productCategoryDao;
    }

    public void register(final ProductRequestDto productRequestDto) {
        final ProductEntity productEntity = new ProductEntity(
            productRequestDto.getName(),
            productRequestDto.getImageUrl(),
            productRequestDto.getPrice(),
            productRequestDto.getDescription()
        );
        final Long savedProductId = productDao.save(productEntity);

        for (final Long categoryId : productRequestDto.getCategoryIds()) {
            productCategoryDao.save(new ProductCategoryEntity(savedProductId, categoryId));
        }
    }
}
