package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.item.Item;
import cart.repository.dao.CartItemDao;
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
        List<Item> actual = cartItemDao.findAllByCartId(1L);

        assertAll(
                () -> assertThat(actual).hasSizeGreaterThanOrEqualTo(1),
                () -> assertThat(actual.get(0).getId()).isPositive(),
                () -> assertThat(actual.get(0).getName()).isEqualTo("자전거1"),
                () -> assertThat(actual.get(0).getImageUrl())
                        .isEqualTo("https://www.altonsports.com/prdimg/get/21-INNOZEN24_P_01%281060X600%29.jpg"),
                () -> assertThat(actual.get(0).getPrice()).isEqualTo(10_000)
        );
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void deleteSuccess() {
        int actual = cartItemDao.delete(1L, 1L);

        assertThat(actual).isOne();
    }
}
