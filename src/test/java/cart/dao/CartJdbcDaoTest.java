package cart.dao;

import cart.entity.CartEntity;
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

    private CartJdbcDao cartJdbcDao;

    @BeforeEach
    void setUp() {
        this.cartJdbcDao = new CartJdbcDao(jdbcTemplate);
    }

    @Test
    void save() {
        final CartEntity cartEntity = new CartEntity(1L, 100L, 100L);
        cartJdbcDao.save(cartEntity);

        final List<CartEntity> cartEntities = cartJdbcDao.findByMemberId(100L);
        assertAll(
                () -> Assertions.assertThat(cartEntities.get(0).getId()).isEqualTo(1L),
                () -> Assertions.assertThat(cartEntities.get(0).getMemberId()).isEqualTo(100L),
                () -> Assertions.assertThat(cartEntities.get(0).getProductId()).isEqualTo(100L)
        );
    }

    @Test
    void deleteById() {
        final CartEntity cartEntity = new CartEntity(2L, 101L, 101L);
        cartJdbcDao.save(cartEntity);
        cartJdbcDao.deleteById(101L, 101L);

        final List<CartEntity> cartEntities = cartJdbcDao.findByMemberId(101L);
        assertAll(
                () -> Assertions.assertThat(cartEntities.size()).isEqualTo(0)
        );
    }
}