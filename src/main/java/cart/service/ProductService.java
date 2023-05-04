package cart.service;

import cart.dao.CartDao;
import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final ProductCategoryDao productCategoryDao;
    private final CartDao cartDao;

    public ProductService(
            final ProductDao productDao,
            final CategoryDao categoryDao,
            final ProductCategoryDao productCategoryDao,
            final CartDao cartDao
    ) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.productCategoryDao = productCategoryDao;
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProducts() {
        return productDao.findAll()
                .stream()
                .map(product -> {
                    final List<Long> categoryIds = getCategoryIds(product);
                    if (categoryIds.isEmpty()) {
                        return ProductResponseDto.of(product, Collections.emptyList());
                    }
                    final List<CategoryEntity> categories = categoryDao.findAllInIds(categoryIds);
                    return ProductResponseDto.of(product, categories);
                })
                .collect(Collectors.toList());
    }

    private List<Long> getCategoryIds(final ProductEntity product) {
        return productCategoryDao.findAll(product.getId())
                .stream()
                .map(ProductCategoryEntity::getCategoryId)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long registerProduct(final ProductRequestDto productRequestDto) {
        final ProductEntity product = new ProductEntity(
                productRequestDto.getName(),
                productRequestDto.getImageUrl(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription()
        );
        final Long savedProductId = productDao.save(product);

        final List<Long> categoryIds = productRequestDto.getCategoryIds();
        final List<ProductCategoryEntity> productCategories = categoryIds.stream()
                .map(categoryId -> new ProductCategoryEntity(savedProductId, categoryId))
                .collect(Collectors.toList());
        productCategoryDao.saveAll(productCategories);

        return savedProductId;
    }

    @Transactional
    public void updateProduct(final Long productId, final ProductRequestDto productRequestDto) {
        final ProductEntity product = getProduct(productId);
        product.update(
                productRequestDto.getName(),
                productRequestDto.getImageUrl(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription()
        );
        productDao.update(product);

        productCategoryDao.deleteAllByProductId(product.getId());

        final List<ProductCategoryEntity> productCategories = productRequestDto.getCategoryIds()
                .stream()
                .map(categoryId -> new ProductCategoryEntity(product.getId(), categoryId))
                .collect(Collectors.toList());
        productCategoryDao.saveAll(productCategories);
    }

    private ProductEntity getProduct(final Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 상품입니다."));
    }

    @Transactional
    public void removeProduct(final Long productId) {
        final ProductEntity product = getProduct(productId);
        productCategoryDao.deleteAllByProductId(product.getId());
        cartDao.deleteAllByProductId(product.getId());
        productDao.delete(product.getId());
    }
}
