package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.물품추가;
import static woowacourse.Fixtures.사용자추가;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.카트추가;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.헌치;
import static woowacourse.Fixtures.헌치_치킨;
import static woowacourse.Fixtures.헌치_치킨_2;
import static woowacourse.Fixtures.헌치_피자;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
public class CartItemRepositoryTest {

    private final CartItemRepository cartItemRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CartItemRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemRepository = new CartItemRepository(new CartItemDao(jdbcTemplate),
                new ProductDao(jdbcTemplate),
                new CustomerDao(jdbcTemplate));
    }

    @BeforeEach
    void setUp() {
        사용자추가(jdbcTemplate, 헌치);
        물품추가(jdbcTemplate, 치킨);
        물품추가(jdbcTemplate, 피자);
        카트추가(jdbcTemplate, 헌치_치킨);
        카트추가(jdbcTemplate, 헌치_피자);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemRepository.create(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("카트에 담긴 물품의 개수를 수정한다. ")
    @Test
    void updateAll() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        cartItemRepository.create(customerId, productId);

        // when
        List<Cart> carts = cartItemRepository.updateAll(List.of(헌치_치킨_2));

        // then
        assertThat(carts).hasSize(1);
        assertThat(carts.get(0).getQuantity()).isEqualTo(2);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니들을 가져온다.")
    @Test
    void findCartsByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Cart> carts = cartItemRepository.findCartsByCustomerId(customerId);

        // then
        assertThat(carts).hasSize(2);
    }

    @DisplayName("cart Id를 넣으면, 해당 장바구니를 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long cartId = 1L;

        // when
        cartItemRepository.deleteById(cartId);

        // then
        final Long customerId = 1L;
        final List<Cart> carts = cartItemRepository.findCartsByCustomerId(customerId);

        assertThat(carts).hasSize(1);
    }
}
