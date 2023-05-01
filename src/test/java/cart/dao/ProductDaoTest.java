package cart.dao;

import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("ID로 상품을 조회한다.")
    void findById() {
        final ProductEntity productEntity = new ProductEntity(
                null,
                "다즐",
                "스플릿.com",
                10000000,
                "다즐짱"
        );
        final Long savedProductId = productDao.save(productEntity);

        final ProductEntity result = productDao.findById(savedProductId).get();

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(savedProductId),
                () -> assertThat(result.getName()).isEqualTo("다즐"),
                () -> assertThat(result.getImageUrl()).isEqualTo("스플릿.com"),
                () -> assertThat(result.getPrice()).isEqualTo(10000000),
                () -> assertThat(result.getDescription()).isEqualTo("다즐짱")
        );
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void findAll() {
        final ProductEntity firstProductEntity = new ProductEntity(
                null,
                "다즐",
                "스플릿.com",
                10000000,
                "다즐짱"
        );
        final ProductEntity secondProductEntity = new ProductEntity(
                null,
                "다즐",
                "스플릿.com",
                10000000,
                "다즐짱"
        );

        final Long savedFirstProductId = productDao.save(firstProductEntity);
        final Long savedSecondProductId = productDao.save(secondProductEntity);

        assertThat(productDao.findAll()).hasSize(2);
        assertThat(productDao.findAll()).map(ProductEntity::getId)
                .containsExactly(savedFirstProductId, savedSecondProductId);
    }

    @Test
    @DisplayName("상품을 저장한다.")
    void save() {
        final ProductEntity productEntity = new ProductEntity(
                null,
                "다즐",
                "스플릿.com",
                10000000,
                "다즐짱"
        );

        final Long savedProductId = productDao.save(productEntity);

        assertThat(savedProductId).isNotNull();
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void update() {
        final ProductEntity productEntity = new ProductEntity(
                null,
                "다즐",
                "스플릿.com",
                10000000,
                "다즐짱"
        );
        final Long savedProductId = productDao.save(productEntity);
        final ProductEntity findProductEntity = productDao.findById(savedProductId).get();
        findProductEntity.update("name", "imageUrl", 1000, "description");

        productDao.update(findProductEntity);
        final ProductEntity result = productDao.findById(findProductEntity.getId()).get();

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("name"),
                () -> assertThat(result.getImageUrl()).isEqualTo("imageUrl"),
                () -> assertThat(result.getPrice()).isEqualTo(1000),
                () -> assertThat(result.getDescription()).isEqualTo("description")
        );
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete() {
        final ProductEntity productEntity = new ProductEntity(
                null,
                "다즐",
                "스플릿.com",
                10000000,
                "다즐짱"
        );
        final Long savedProductId = productDao.save(productEntity);

        productDao.delete(savedProductId);

        assertThat(productDao.findAll()).hasSize(0);
    }
}
