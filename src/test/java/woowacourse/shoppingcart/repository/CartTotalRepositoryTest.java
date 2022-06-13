package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static woowacourse.Fixtures.물품추가;
import static woowacourse.Fixtures.사용자추가;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.카트추가;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.헌치;
import static woowacourse.Fixtures.헌치_치킨;
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
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CartTotalRepositoryTest {

    private final CartTotalRepository cartTotalRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CartTotalRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartTotalRepository = new CartTotalRepository(new CartItemDao(jdbcTemplate),
                new ProductDao(jdbcTemplate),
                new CustomerDao(jdbcTemplate)
        );
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
    void addCartItems() {
        // given
        final Long customerId = 1L;
        final Long productId = 10L;

        // when
        cartTotalRepository.createAll(customerId, List.of(productId));

        // then
        assertDoesNotThrow(() -> cartTotalRepository.findIdByCustomerIdAndProductId(customerId, productId));
    }

    @DisplayName("카트에 담긴 물품의 개수를 수정한다. ")
    @Test
    void update() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        cartTotalRepository.createAll(customerId, List.of(productId));
        Long cartId = cartTotalRepository.findIdByCustomerIdAndProductId(customerId, productId);
        Cart cartOne = cartTotalRepository.findById(cartId);

        // when
        Cart cart = cartTotalRepository.update(
                new CartEntity(cartOne.getId(), customerId, productId, cartOne.getQuantity()));

        // then
        assertThat(cart.getQuantity()).isEqualTo(1);
        assertThat(cart.getId()).isEqualTo(1L);
    }

    @DisplayName("카트에 담긴 모든 물품의 개수를 수정한다.")
    @Test
    void updateAll() {
        // given
        final Long 헌치아이디 = 헌치.getId();

        // when
        cartTotalRepository.updateAll(List.of(plusQuantity(헌치_치킨)));

        // then
        List<Cart> carts = cartTotalRepository.findCartsByCustomerId(헌치아이디);
        assertThat(carts.get(0).getQuantity()).isEqualTo(2);
        assertThat(carts.get(1).getQuantity()).isEqualTo(2);
    }

    public CartEntity plusQuantity(CartEntity cartEntity) {
        return new CartEntity(cartEntity.getId(), cartEntity.getCustomerId(), cartEntity.getProductId(),
                cartEntity.getQuantity() + 1);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니들을 가져온다.")
    @Test
    void findCartsByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Cart> carts = cartTotalRepository.findCartsByCustomerId(customerId);

        // then
        assertThat(carts).hasSize(2);
    }

    @DisplayName("cart Id를 넣으면, 해당 장바구니를 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long cartId = 1L;

        // when
        cartTotalRepository.deleteById(cartId);

        // then
        final Long customerId = 1L;
        final List<Cart> carts = cartTotalRepository.findCartsByCustomerId(customerId);

        assertThat(carts).hasSize(1);
    }
}
