package cart.dao;

import cart.entity.CartProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql({"/test.sql"})
class CartJdbcDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CarProductJdbcDao cartJdbcDao;

    @BeforeEach
    void setUp() {
        this.cartJdbcDao = new CarProductJdbcDao(jdbcTemplate);
    }

    @Test
    void save() {
        final CartProductEntity cartProductEntity = new CartProductEntity(1L, 100L, 100L);
        cartJdbcDao.save(cartProductEntity);

        final List<CartProductEntity> cartEntities = cartJdbcDao.findByMemberId(100L);
        assertAll(
                () -> Assertions.assertThat(cartEntities.get(0).getId()).isEqualTo(1L),
                () -> Assertions.assertThat(cartEntities.get(0).getMemberId()).isEqualTo(100L),
                () -> Assertions.assertThat(cartEntities.get(0).getProductId()).isEqualTo(100L)
        );
    }

    @Test
    void deleteById() {
        final CartProductEntity cartProductEntity = new CartProductEntity(2L, 101L, 101L);
        cartJdbcDao.save(cartProductEntity);
        cartJdbcDao.deleteById(new CartProductEntity(101L, 101L));

        final List<CartProductEntity> cartEntities = cartJdbcDao.findByMemberId(101L);
        assertAll(
                () -> Assertions.assertThat(cartEntities.size()).isEqualTo(0)
        );
    }
}