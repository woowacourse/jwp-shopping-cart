package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
    @DisplayName("상품 카테고리를 저장한다.")
    void save() {
        //given
        final ProductEntity productEntity = new ProductEntity(
            1L,
            "name",
            "image_url",
            1000,
            "description"
        );
        final Long savedProductId = productDao.save(productEntity);
        final ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(savedProductId, 1L);

        //when
        //then
        assertThat(productCategoryDao.save(productCategoryEntity)).isNotNull();
    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("상품 ID가 null 일 경우 오류를 던진다.")
        void findAllWithNullProductId() {
            assertThatThrownBy(() -> productCategoryDao.findAll(null))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("상품 ID에 대한 상품 카테고리 목록을 조회한다.")
        void findAll() {
            //given
            final ProductEntity productEntity = new ProductEntity(
                1L,
                "name",
                "image_url",
                1000,
                "description"
            );
            final Long savedProductId = productDao.save(productEntity);
            final ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(savedProductId, 1L);
            productCategoryDao.save(productCategoryEntity);

            //when
            final List<ProductCategoryEntity> productCategoryEntities = productCategoryDao.findAll(savedProductId);

            //then
            assertThat(productCategoryEntities).hasSize(1);
        }
    }
}
