package cart.dao;

import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductCategoryDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
        productCategoryDao = new ProductCategoryDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품 ID에 대한 상품 카테고리 목록을 조회한다.")
    void findAll() {
        final ProductEntity productEntity = new ProductEntity(
                1L,
                "name",
                "image_url",
                1000,
                "description"
        );
        final Long savedProductId = productDao.save(productEntity);
        productCategoryDao.saveAll(List.of(new ProductCategoryEntity(savedProductId, 1L)));

        assertThat(productCategoryDao.findAll(savedProductId)).hasSize(1);
    }

    @Test
    @DisplayName("상품 카테고리를 모두 저장한다.")
    void saveAll() {
        final ProductEntity productEntity = new ProductEntity(
                1L,
                "name",
                "image_url",
                1000,
                "description"
        );
        final Long savedProductId = productDao.save(productEntity);
        final List<ProductCategoryEntity> productCategoryEntities = List.of(
                new ProductCategoryEntity(savedProductId, 1L),
                new ProductCategoryEntity(savedProductId, 2L)
        );

        assertThat(productCategoryDao.saveAll(productCategoryEntities)).isEqualTo(2);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 카테고리를 모두 삭제한다.")
    void deleteAll() {
        final ProductEntity productEntity = new ProductEntity(
                1L,
                "name",
                "image_url",
                1000,
                "description"
        );
        final Long savedProductId = productDao.save(productEntity);
        productCategoryDao.saveAll(List.of(new ProductCategoryEntity(savedProductId, 1L)));
        final List<ProductCategoryEntity> savedProductCategories = productCategoryDao.findAll(savedProductId);
        final List<Long> savedProductCategoryIds = savedProductCategories.stream()
                .map(ProductCategoryEntity::getId)
                .collect(Collectors.toList());

        productCategoryDao.deleteAll(savedProductCategoryIds);

        assertThat(productCategoryDao.findAll(savedProductId)).hasSize(0);
    }
}
