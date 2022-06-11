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
public class CartItemRepositoryTest {

    private final CartItemRepository cartItemRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CartItemRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemRepository = new CartItemRepository(new CartItemDao(jdbcTemplate),
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
        cartItemRepository.createAll(customerId, List.of(productId));

        // then
        assertDoesNotThrow(() -> cartItemRepository.findIdByCustomerIdAndProductId(customerId,productId));
    }

    @DisplayName("카트에 이미 해당 아이템이 있을 시 개수 +1을 한다 ")
    @Test
    void addCartItem_plus() {
        // given
        final Long customerId = 1L;
        final Long productId = 3L;

        // when
        cartItemRepository.createAll(customerId, List.of(productId));
        Long id = cartItemRepository.findIdByCustomerIdAndProductId(customerId, productId);
        cartItemRepository.plusQuantityByIds(List.of(id));

        // then
        assertThat(cartItemRepository.findById(id).getQuantity()).isEqualTo(2);
    }

    @DisplayName("카트에 담긴 물품의 개수를 수정한다. ")
    @Test
    void update() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        cartItemRepository.createAll(customerId, List.of(productId));
        Long cartId = cartItemRepository.findIdByCustomerIdAndProductId(customerId, productId);
        Cart cartOne = cartItemRepository.findById(cartId);

        // when
        Cart cart = cartItemRepository.update(
                new CartEntity(cartOne.getId(), customerId, productId, cartOne.getQuantity()));

        // then
        assertThat(cart.getQuantity()).isEqualTo(1);
        assertThat(cart.getId()).isEqualTo(1L);
    }

    @DisplayName("카트에 담긴 물품의 개수를 1 증가한다. ")
    @Test
    void plusQuantityByIds() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        cartItemRepository.createAll(customerId, List.of(productId));
        Long cartId = cartItemRepository.findIdByCustomerIdAndProductId(customerId, productId);

        // when
        cartItemRepository.plusQuantityByIds(List.of(cartId));
        Cart cart = cartItemRepository.findById(cartId);

        // then
        assertThat(cart.getQuantity()).isEqualTo(2);
        assertThat(cart.getId()).isEqualTo(1L);
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
