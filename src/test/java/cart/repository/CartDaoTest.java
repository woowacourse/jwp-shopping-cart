package cart.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.CartItemEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/test.sql")
class CartDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void add_success() {
        // given
        long memberId = 1L;
        long productId = 1L;

        // when
        long cartId = cartDao.add(memberId, productId);

        // then
        assertThat(cartId).isEqualTo(2);
        assertThat(cartDao.findAllByMemberId(memberId)).hasSize(2);
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void delete_success() {
        // given
        long memberId = 1L;
        long productId = 1L;

        long cartId = cartDao.add(memberId, productId);

        // when
        cartDao.deleteById(memberId, cartId);

        // then
        assertThat(cartDao.findAllByMemberId(memberId)).hasSize(1);
    }

    @Test
    @DisplayName("특정 사용자의 장바구니에 있는 상품들을 반환한다.")
    void findAllByMemberId_success() {
        // given
        long memberId = 1L;

        // when
        List<CartItemEntity> all = cartDao.findAllByMemberId(memberId);
        CartItemEntity product = all.get(0);

        // then
        assertAll(
                () -> assertThat(all).hasSize(1),
                () -> assertThat(product.getId()).isEqualTo(1),
                () -> assertThat(product.getName()).isEqualTo("오감자"),
                () -> assertThat(product.getPrice()).isEqualTo(1000)
        );
    }

    @ParameterizedTest(name = "cartId 를 통해 특정 상품이 장바구니에 있는지 확인한다.")
    @CsvSource({"1,true", "2,false"})
    void existsById_success(long cartId, boolean expected) {
        // when
        Boolean actual = cartDao.existsById(cartId);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
