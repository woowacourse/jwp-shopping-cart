package cart.service;

import cart.dao.CartDao;
import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import cart.exception.NotFoundException;
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
    public List<ProductResponse> findProducts() {
        return productDao.findAll()
                .stream()
                .map(this::generateProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse generateProductResponse(final ProductEntity product) {
        final List<Long> categoryIds = getCategoryIds(product);
        if (categoryIds.isEmpty()) {
            return ProductResponse.of(product, Collections.emptyList());
        }
        final List<CategoryEntity> categories = categoryDao.findAllInIds(categoryIds);
        return ProductResponse.of(product, categories);
    }

    private List<Long> getCategoryIds(final ProductEntity product) {
        return productCategoryDao.findAll(product.getId())
                .stream()
                .map(ProductCategoryEntity::getCategoryId)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long registerProduct(final ProductRequest productRequest) {
        final ProductEntity product = new ProductEntity(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice(),
                productRequest.getDescription()
        );
        final Long savedProductId = productDao.save(product);

        final List<Long> categoryIds = productRequest.getCategoryIds();
        final List<ProductCategoryEntity> productCategories = categoryIds.stream()
                .map(categoryId -> new ProductCategoryEntity(savedProductId, categoryId))
                .collect(Collectors.toList());
        productCategoryDao.saveAll(productCategories);

        return savedProductId;
    }

    @Transactional
    public void updateProduct(final Long productId, final ProductRequest productRequest) {
        final ProductEntity product = getProduct(productId);
        product.update(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice(),
                productRequest.getDescription()
        );
        productDao.update(product);

        productCategoryDao.deleteAllByProductId(product.getId());

        final List<ProductCategoryEntity> productCategories = productRequest.getCategoryIds()
                .stream()
                .map(categoryId -> new ProductCategoryEntity(product.getId(), categoryId))
                .collect(Collectors.toList());
        productCategoryDao.saveAll(productCategories);
    }

    private ProductEntity getProduct(final Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 상품입니다."));
    }

    @Transactional
    public void removeProduct(final Long productId) {
        final ProductEntity product = getProduct(productId);
        productCategoryDao.deleteAllByProductId(product.getId());
        cartDao.deleteAllByProductId(product.getId());
        productDao.delete(product.getId());
    }
}
