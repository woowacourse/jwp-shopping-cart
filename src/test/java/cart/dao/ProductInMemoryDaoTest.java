package cart.dao;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductInMemoryDaoTest {

    private ProductInMemoryDao productInMemoryDao;
    private Integer insertedId;

    @BeforeEach
    void setUp() {
        this.productInMemoryDao = new ProductInMemoryDao();

        final ProductEntity productEntity = new ProductEntity(1, "비버", "A", 1000L);
        insertedId = productInMemoryDao.insert(productEntity);
    }

    @Test
    @DisplayName("삽입 테스트")
    void insert() {
        assertThat(insertedId).isNotNull();
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {
        final ProductEntity productEntity = new ProductEntity(1, "비버", "A", 100000L);
        productInMemoryDao.update(productEntity);

        final ProductEntity result = productInMemoryDao.select(insertedId);
        assertThat(result.getPrice()).isEqualTo(100000L);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteById() {
        productInMemoryDao.deleteById(insertedId);

        assertThat(productInMemoryDao.findAll().size()).isZero();
    }

    @Test
    @DisplayName("조회 테스트")
    void select() {
        final ProductEntity expectEntity = new ProductEntity(1, "비버", "A", 1000L);
        final ProductEntity entity = productInMemoryDao.select(insertedId);

        assertThat(entity).isEqualTo(expectEntity);
    }

    @Test
    @DisplayName("전체조회 테스트")
    void findAll() {
        final ProductEntity expectEntity = new ProductEntity(1, "비버", "A", 1000L);

        assertThat(productInMemoryDao.findAll()).containsOnly(expectEntity);
    }
}