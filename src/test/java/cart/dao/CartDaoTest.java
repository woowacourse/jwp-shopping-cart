package cart.dao;

import cart.domain.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Transactional
class CartDaoTest {

    CartDao cartDao;
    ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        productDao = new ProductDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("카트 데이터베이스에 카트 데이터를 추가한다")
    void insert() {
        int cartId = cartDao.insert(1, 1);

        assertThat(cartId).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 id에 해당하는 카트 정보를 제거한다.")
    void delete() {
        int cartId = cartDao.insert(1, 1);

        cartDao.deleteById(cartId);

        assertThat(cartDao.findAll(1)).hasSize(0);
    }

    @Test
    @DisplayName("DB에 등록된 모든 카트 정보를 찾는다")
    void findAll() {
        long productId1 = productDao.insert(new ProductEntity.Builder().name("pizza").image("image1").price(10000).build());
        long productId2 = productDao.insert(new ProductEntity.Builder().name("chiken").image("image2").price(20000).build());
        cartDao.insert(1, (int) productId1);
        cartDao.insert(1, (int) productId2);

        assertThat(cartDao.findAll(1)).hasSize(2);
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE Carts");
        jdbcTemplate.update("ALTER TABLE Carts ALTER COLUMN id RESTART");
    }

}