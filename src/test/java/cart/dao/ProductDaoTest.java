package cart.dao;

import static cart.TestFixture.IMAGE_CHICKEN;
import static cart.TestFixture.IMAGE_ICE_CREAM;
import static cart.TestFixture.IMAGE_VANILLA_LATTE;
import static cart.TestFixture.NAME_CHICKEN;
import static cart.TestFixture.NAME_ICE_CREAM;
import static cart.TestFixture.NAME_VANILLA_LATTE;
import static cart.TestFixture.PRICE_CHICKEN;
import static cart.TestFixture.PRICE_ICE_CREAM;
import static cart.TestFixture.PRICE_VANILLA_LATTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.dao.dto.ProductDto;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.productDao = new ProductDao(jdbcTemplate);
        productDao.insert(NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);
        productDao.insert(NAME_ICE_CREAM, IMAGE_ICE_CREAM, PRICE_ICE_CREAM);
        productDao.insert(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE);
    }

    @Test
    @DisplayName("상품 삽입")
    void insert() {
        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .contains(
                        tuple(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE)
                );
    }

    @Test
    @DisplayName("상품 하나 조회")
    void select() {
        final ProductDto entity = productDao.select(getGreatestId());

        assertThat(entity)
                .extracting("name", "image", "price")
                .containsExactly(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void findAll() {
        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .containsExactlyInAnyOrder(
                        tuple(NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN),
                        tuple(NAME_ICE_CREAM, IMAGE_ICE_CREAM, PRICE_ICE_CREAM),
                        tuple(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE)
                );
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        productDao.update(getGreatestId(), NAME_VANILLA_LATTE, "VERY_BIG_IMAGE", PRICE_VANILLA_LATTE);

        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .contains(tuple(NAME_VANILLA_LATTE, "VERY_BIG_IMAGE", PRICE_VANILLA_LATTE));
    }

    @Test
    @DisplayName("수정된 상품 수 반환")
    void count_updated() {
        int updatedCount = productDao.update(getGreatestId(), NAME_VANILLA_LATTE, "VERY_BIG_IMAGE",
                PRICE_VANILLA_LATTE);

        assertThat(updatedCount).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteById() {
        productDao.deleteById(getGreatestId());

        assertThat(productDao.findAll())
                .extracting("name")
                .doesNotContain(NAME_VANILLA_LATTE);
    }

    @Test
    @DisplayName("삭제된 상품 수 반환")
    void count_deleted() {
        final int previousCount = productDao.findAll().size();
        int deletedCount = productDao.deleteById(getGreatestId());

        assertThat(productDao.findAll()).hasSize(previousCount - deletedCount);
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}