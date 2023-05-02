package cart.dao;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductJdbcDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductJdbcDao productJdbcDao;
    private Long insertedId;

    @BeforeEach
    void setUp() {
        this.productJdbcDao = new ProductJdbcDao(jdbcTemplate);

        final ProductEntity productEntity = new ProductEntity(null, "비버", "A", 1000L);
        insertedId = Long.valueOf(productJdbcDao.insert(productEntity));
        System.out.println("insertedId = " + insertedId);
    }

    @Test
    @DisplayName("삽입 테스트")
    void insert() {
        assertThat(insertedId).isNotNull();
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {
        final ProductEntity productEntity = new ProductEntity(insertedId, "비버", "A", 100000L);
        productJdbcDao.update(productEntity);

        final Optional<ProductEntity> ProductEntityById = productJdbcDao.findById(Math.toIntExact(insertedId));
        assertThat(ProductEntityById.get().getPrice()).isEqualTo(100000L);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteById() {
        productJdbcDao.deleteById(Math.toIntExact(insertedId));

        assertThat(productJdbcDao.findAll().size()).isZero();
    }

    @Test
    @DisplayName("조회 테스트")
    void select() {
        final ProductEntity expectEntity = new ProductEntity(insertedId, "비버", "A", 1000L);
        final Optional<ProductEntity> ProductEntityById = productJdbcDao.findById(Math.toIntExact(insertedId));

        assertThat(ProductEntityById.get()).isEqualTo(expectEntity);
    }

    @Test
    @DisplayName("전체조회 테스트")
    void findAll() {
        final ProductEntity expectEntity = new ProductEntity(insertedId, "비버", "A", 1000L);

        assertThat(productJdbcDao.findAll()).containsOnly(expectEntity);
    }
}