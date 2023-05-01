package cart.service;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.CategoryResponseDto;
import cart.dto.response.ProductResponseDto;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final ProductCategoryDao productCategoryDao;

    public ProductService(
        final ProductDao productDao,
        final CategoryDao categoryDao,
        final ProductCategoryDao productCategoryDao
    ) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.productCategoryDao = productCategoryDao;
    }

    @Transactional
    public Long register(final ProductRequestDto productRequestDto) {
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
        return savedProductId;
    }

    public List<ProductResponseDto> findProducts() {
        return productDao.findAll().stream()
            .map(productEntity -> {
                final List<Long> categoryIds = getCategoryIds(productEntity);
                final List<CategoryEntity> categoryEntities = categoryDao.findAllInId(categoryIds);
                return ProductResponseDto.of(productEntity, categoryEntities);
            })
            .collect(Collectors.toList());
    }

    private List<Long> getCategoryIds(final ProductEntity productEntity) {
        final ProductEntity savedProductEntity = getSavedProductEntity(productEntity.getId());

        return productCategoryDao.findAll(savedProductEntity.getId())
            .stream()
            .map(ProductCategoryEntity::getCategoryId)
            .collect(Collectors.toList());
    }

    private ProductEntity getSavedProductEntity(final Long id) {
        return productDao.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    public List<CategoryResponseDto> findCategories() {
        return categoryDao.findAll().stream()
            .map(CategoryResponseDto::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void update(final Long id, final ProductRequestDto productRequestDto) {
        final ProductEntity savedProductEntity = getSavedProductEntity(id);
        savedProductEntity.update(
            productRequestDto.getName(),
            productRequestDto.getImageUrl(),
            productRequestDto.getPrice(),
            productRequestDto.getDescription()
        );
        productDao.update(savedProductEntity);
        for (ProductCategoryEntity productCategoryEntity : productCategoryDao.findAll(id)) {
            productCategoryDao.delete(productCategoryEntity.getId());
        }
        for (final Long categoryId : productRequestDto.getCategoryIds()) {
            productCategoryDao.save(new ProductCategoryEntity(id, categoryId));
        }
    }

    @Transactional
    public void delete(final Long id) {
        final ProductEntity savedProductEntity = getSavedProductEntity(id);
        final List<ProductCategoryEntity> productCategoryEntities = productCategoryDao.findAll(id);
        for (ProductCategoryEntity productCategoryEntity : productCategoryEntities) {
            productCategoryDao.delete(productCategoryEntity.getId());
        }
        productDao.delete(savedProductEntity.getId());
    }
}
