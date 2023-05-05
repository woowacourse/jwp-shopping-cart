package cart.service;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.ProductCategoryDto;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.CategoryResponseDto;
import cart.entity.category.CategoryEntity;
import cart.entity.product.ProductEntity;
import cart.entity.productcategory.ProductCategoryEntity;
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
        final Long savedProductId = productDao.save(ProductEntity.of(productRequestDto));
        registerAllProductCategory(productRequestDto, savedProductId);

        return savedProductId;
    }

    private void registerAllProductCategory(final ProductRequestDto productRequestDto, final Long savedProductId) {
        for (final Long categoryId : productRequestDto.getCategoryIds()) {
            productCategoryDao.save(new ProductCategoryEntity(savedProductId, categoryId));
        }
    }

    public List<ProductCategoryDto> findAll() {
        return productDao.findAll().stream()
            .map(productEntity -> {
                final List<Long> categoryIds = findCategoryIds(productEntity);
                final List<CategoryEntity> categoryEntities = categoryDao.findAllInId(categoryIds);
                return ProductCategoryDto.of(productEntity, categoryEntities);
            })
            .collect(Collectors.toList());
    }

    private List<Long> findCategoryIds(final ProductEntity productEntity) {
        final ProductEntity savedProductEntity = findProductById(productEntity.getId());

        return productCategoryDao.findAll(savedProductEntity.getId())
            .stream()
            .map(ProductCategoryEntity::getCategoryId)
            .collect(Collectors.toList());
    }

    private ProductEntity findProductById(final Long id) {
        return productDao.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    public List<CategoryResponseDto> findCategories() {
        return CategoryResponseDto.listOf(categoryDao.findAll());
    }

    @Transactional
    public void update(final Long id, final ProductRequestDto productRequestDto) {
        final ProductEntity savedProductEntity = findProductById(id);
        savedProductEntity.update(
            productRequestDto.getName(),
            productRequestDto.getImageUrl(),
            productRequestDto.getPrice(),
            productRequestDto.getDescription()
        );

        productDao.update(savedProductEntity);
        deleteAllLegacyProductCategory(id);
        registerAllProductCategory(productRequestDto, id);
    }

    private void deleteAllLegacyProductCategory(final Long id) {
        for (ProductCategoryEntity productCategoryEntity : productCategoryDao.findAll(id)) {
            productCategoryDao.delete(productCategoryEntity.getId());
        }
    }

    @Transactional
    public void delete(final Long id) {
        final ProductEntity savedProductEntity = findProductById(id);
        deleteAllLegacyProductCategory(id);

        productDao.delete(savedProductEntity.getId());
    }
}
