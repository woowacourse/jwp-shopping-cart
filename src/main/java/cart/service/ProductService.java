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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public Long registerProduct(final ProductRequestDto productRequestDto) {
        final ProductEntity product = new ProductEntity(
                productRequestDto.getName(),
                productRequestDto.getImageUrl(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription()
        );
        final Long savedProductId = productDao.save(product);

        final List<Long> categoryIds = productRequestDto.getCategoryIds();
        for (final Long categoryId : categoryIds) {
            productCategoryDao.save(new ProductCategoryEntity(savedProductId, categoryId));
        }

        return savedProductId;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProducts() {
        return productDao.findAll().stream()
                .map(product -> {
                    final List<Long> categoryIds = getCategoryIds(product);
                    final List<CategoryEntity> categories = categoryDao.findAllInId(categoryIds);
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

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findCategories() {
        return categoryDao.findAll().stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProduct(final Long id, final ProductRequestDto productRequestDto) {
        final ProductEntity savedProduct = getSavedProductEntity(id);
        savedProduct.update(
                productRequestDto.getName(),
                productRequestDto.getImageUrl(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription()
        );
        productDao.update(savedProduct);

        final List<ProductCategoryEntity> productCategories = productCategoryDao.findAll(savedProduct.getId());
        for (final ProductCategoryEntity productCategory : productCategories) {
            productCategoryDao.delete(productCategory.getId());
        }
        final List<Long> categoryIds = productRequestDto.getCategoryIds();
        for (final Long categoryId : categoryIds) {
            productCategoryDao.save(new ProductCategoryEntity(savedProduct.getId(), categoryId));
        }
    }

    private ProductEntity getSavedProductEntity(final Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    @Transactional
    public void removeProduct(final Long id) {
        final ProductEntity savedProduct = getSavedProductEntity(id);
        final List<ProductCategoryEntity> productCategories = productCategoryDao.findAll(savedProduct.getId());
        for (final ProductCategoryEntity productCategory : productCategories) {
            productCategoryDao.delete(productCategory.getId());
        }
        productDao.delete(savedProduct.getId());
    }
}
