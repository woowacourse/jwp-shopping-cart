package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.repository.dao.CartItemDao;
import cart.repository.dao.entity.CartItemEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartItemDaoTest {

    CartItemDao cartItemDao;
    Long cartId;

    @BeforeEach
    void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        cartId = cartItemDao.insert(1L, 1L);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void insertSuccess() {
        Long actual = cartItemDao.insert(1L, 2L);

        assertThat(actual).isPositive();
    }

    @Test
    @DisplayName("장바구니의 모든 상품을 조회한다.")
    void findAllByCartIdSuccess() {
        List<CartItemEntity> actual = cartItemDao.findAllByCartId(1L);

        assertAll(
                () -> assertThat(actual).hasSizeGreaterThanOrEqualTo(1),
                () -> assertThat(actual.get(0).getId()).isPositive(),
                () -> assertThat(actual.get(0).getCartId()).isEqualTo(1L),
                () -> assertThat(actual.get(0).getItemId()).isPositive()
        );
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void deleteSuccess() {
        int actual = cartItemDao.delete(1L, 1L);

        assertThat(actual).isOne();
    }
}
