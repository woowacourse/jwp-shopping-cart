package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
}
