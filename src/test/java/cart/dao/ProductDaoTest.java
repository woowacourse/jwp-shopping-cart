package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.TestFixture;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.productDao = new ProductDao(jdbcTemplate);

        productDao.insert("땡칠", TestFixture.IMAGE_0CHIL, 1000L);
        productDao.insert("비버", TestFixture.IMAGE_BEAVER, 1000L);
        productDao.insert("코다", TestFixture.IMAGE_KODA, 1000000L);
    }

    @Test
    @DisplayName("상품 삽입")
    void insert() {
        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .contains(
                        tuple("비버", TestFixture.IMAGE_BEAVER, 1000L)
                );
    }

    @Test
    @DisplayName("상품 하나 조회")
    void select() {
        final ProductEntity entity = productDao.select(getGreatestId());

        assertThat(entity)
                .extracting("name", "image", "price")
                .containsExactly("코다", TestFixture.IMAGE_KODA, 1000000L);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void findAll() {
        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .containsExactlyInAnyOrder(
                        tuple("땡칠", TestFixture.IMAGE_0CHIL, 1000L),
                        tuple("비버", TestFixture.IMAGE_BEAVER, 1000L),
                        tuple("코다", TestFixture.IMAGE_KODA, 1000000L)
                );
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        productDao.update(getGreatestId(), "코다", "VERY_BIG_IMAGE", 100L);

        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .contains(tuple("코다", "VERY_BIG_IMAGE", 100L));
    }

    @Test
    @DisplayName("수정된 상품 수 반환")
    void count_updated() {
        int updatedCount = productDao.update(getGreatestId(), "코다", "VERY_BIG_IMAGE", 100L);

        assertThat(updatedCount).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteById() {
        productDao.deleteById(getGreatestId());

        assertThat(productDao.findAll())
                .extracting("name")
                .doesNotContain("코다");
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