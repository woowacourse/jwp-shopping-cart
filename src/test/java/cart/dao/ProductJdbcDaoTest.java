package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.entity.ProductEntity;
import cart.repository.ProductDto;

@JdbcTest
class ProductJdbcDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductJdbcDao productDao;

    @BeforeEach
    void setUp() {
        this.productDao = new ProductJdbcDao(jdbcTemplate);

        productDao.insert(new ProductDto("땡칠", "https://avatars.githubusercontent.com/u/39221443", 1000L));
        productDao.insert(new ProductDto("비버", "https://avatars.githubusercontent.com/u/109223081", 1000L));
        productDao.insert(new ProductDto("코다", "https://avatars.githubusercontent.com/u/63405904", 1000000L));
    }

    @Test
    @DisplayName("상품 삽입")
    void insert() {
        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .contains(
                        tuple("비버", "https://avatars.githubusercontent.com/u/109223081", 1000L)
                );
    }

    @Test
    @DisplayName("상품 하나 조회")
    void select() {
        final ProductEntity entity = productDao.select(getGreatestId());

        assertThat(entity)
                .extracting("name", "image", "price")
                .containsExactly("코다", "https://avatars.githubusercontent.com/u/63405904", 1000000L);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void findAll() {
        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .containsExactlyInAnyOrder(
                        tuple("땡칠", "https://avatars.githubusercontent.com/u/39221443", 1000L),
                        tuple("비버", "https://avatars.githubusercontent.com/u/109223081", 1000L),
                        tuple("코다", "https://avatars.githubusercontent.com/u/63405904", 1000000L)
                );
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        productDao.update(getGreatestId(), new ProductDto("코다", "VERY_BIG_IMAGE", 100L));

        assertThat(productDao.findAll())
                .extracting("name", "image", "price")
                .contains(tuple("코다", "VERY_BIG_IMAGE", 100L));
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteById() {
        productDao.deleteById(getGreatestId());

        assertThat(productDao.findAll())
                .extracting("name")
                .doesNotContain("코다");
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}