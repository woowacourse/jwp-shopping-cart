package cart.dao;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import java.util.Optional;

import static cart.fixture.ProductEntityFixture.TEST_PRODUCT_BEAVER_ENTITY_ID;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductInMemoryDaoTest {

    private ProductInMemoryDao productInMemoryDao;
    private Integer insertedId;

    @BeforeEach
    void setUp() {
        this.productInMemoryDao = new ProductInMemoryDao();


        insertedId = productInMemoryDao.insert(TEST_PRODUCT_BEAVER_ENTITY_ID);
    }

    @Test
    @DisplayName("삽입 테스트")
    void insert() {
        assertThat(insertedId).isNotNull();
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {
        productInMemoryDao.update(TEST_PRODUCT_BEAVER_ENTITY_ID);

        final Optional<ProductEntity> productEntityById = productInMemoryDao.findById(Long.valueOf(insertedId));
        assertThat(productEntityById.get().getPrice()).isEqualTo(100000L);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteById() {
        System.out.println(insertedId);

        productInMemoryDao.deleteById(Long.valueOf(insertedId));

        assertThat(productInMemoryDao.findAll().size()).isZero();
    }

    @Test
    @DisplayName("조회 테스트")
    void select() {
        final Optional<ProductEntity> byId = productInMemoryDao.findById(Long.valueOf(insertedId));

        assertThat(byId.get()).isEqualTo(TEST_PRODUCT_BEAVER_ENTITY_ID);
    }

    @Test
    @DisplayName("전체조회 테스트")
    void findAll() {
        assertThat(productInMemoryDao.findAll()).containsOnly(TEST_PRODUCT_BEAVER_ENTITY_ID);
    }
}