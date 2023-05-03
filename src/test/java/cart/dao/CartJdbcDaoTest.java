package cart.dao;

import cart.entity.CartEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class CartJdbcDaoTest {

    @Autowired
    private CartJdbcDao cartJdbcDao;

    @Test
    void save() {
        final CartEntity cartEntity = new CartEntity(1L, 2L, 3L);
        cartJdbcDao.save(cartEntity);

        final List<CartEntity> cartEntities = cartJdbcDao.findByMemberId(3L);
        assertAll(
                () -> Assertions.assertThat(cartEntities.get(0).getId()).isEqualTo(1L),
                () -> Assertions.assertThat(cartEntities.get(0).getMemberId()).isEqualTo(3L),
                () -> Assertions.assertThat(cartEntities.get(0).getProductId()).isEqualTo(2L)
        );
    }

    @Test
    void deleteById() {
        final CartEntity cartEntity = new CartEntity(1L, 2L, 3L);
        cartJdbcDao.save(cartEntity);
        cartJdbcDao.deleteById(3L, 2L);

        final List<CartEntity> cartEntities = cartJdbcDao.findByMemberId(3L);
        assertAll(
                () -> Assertions.assertThat(cartEntities.get(0).getId()).isNotEqualTo(1L),
                () -> Assertions.assertThat(cartEntities.get(0).getMemberId()).isNotEqualTo(3L),
                () -> Assertions.assertThat(cartEntities.get(0).getProductId()).isNotEqualTo(2L)
        );
    }
}