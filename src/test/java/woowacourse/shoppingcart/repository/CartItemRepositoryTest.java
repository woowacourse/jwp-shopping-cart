package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.repository.dao.CartItemDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
class CartItemRepositoryTest {

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemRepositoryTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.cartItemRepository = new CartItemRepository(new CartItemDao(namedParameterJdbcTemplate));
    }

    @DisplayName("사용자의 id 를 이용해서 저장된 모든 장바구니 속 물품들을 조회한다.")
    @Test
    void findCartItemsById() {
        // given
        Long customerId = 1L;

        // when
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCustomerId(customerId);

        // then
        assertAll(
                () -> assertThat(cartItems.size()).isEqualTo(3),
                () -> assertThat(cartItems.stream().map(CartItem::getProductId).collect(Collectors.toList()))
                        .containsExactly(1L, 2L, 3L),
                () -> assertThat(cartItems.stream().map(CartItem::getQuantity).collect(Collectors.toList()))
                        .containsExactly(5, 7, 9)
        );
    }
}
