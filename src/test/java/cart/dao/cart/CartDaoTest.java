package cart.dao.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/schema.sql")
@Sql("/data.sql")
class CartDaoTest {

    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("findAll() : 모든 cart를 가져올 수 있다.")
    void test_findAll() {
        // given
        int resultSize = 2;
        final CartEntity cartEntity = new CartEntity(1L, 2L);
        final CartEntity cartEntity2 = new CartEntity(2L, 1L);

        // when
        cartDao.save(cartEntity);
        cartDao.save(cartEntity2);

        // when & expected
        assertThat(cartDao.findAll()).hasSize(resultSize);
    }

    @Test
    @DisplayName("save() : cart를 저장할 수 있다.")
    void test_save() {
        // given
        final int beforeSize = cartDao.findAll().size();

        cartDao.save(new CartEntity(1L, 2L));

        final int afterSize = cartDao.findAll().size();

        // when & expected
        assertThat(beforeSize + 1).isEqualTo(afterSize);
    }

    @Test
    @DisplayName("deleteById() : id로 cart를 삭제할 수 있다.")
    void test_deleteById() {
        // given
        int beforeSize = cartDao.findAll().size();
        cartDao.deleteById(1L);

        // when
        int afterSize = cartDao.findAll().size();

        // expected
        assertAll(
                () -> assertThat(beforeSize).isEqualTo(afterSize),
                () -> assertThatThrownBy(() -> cartDao.findById(1L))
                        .isInstanceOf(EmptyResultDataAccessException.class)
        );
    }
}
