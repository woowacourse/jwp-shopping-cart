package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.dto.CartItemDto;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate,
                           NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate, namedJdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)", 1L, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)", 1L, 2L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id와 product Id를 넣으면, 해당 장바구니 물품을 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        cartItemDao.deleteCartItem(customerId, productId);

        // then
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @DisplayName("특정 아이템이 특정 커스토머의 장바구니에 있으면 true를 반환한다.")
    @Test
    void isExistItemTest_true() {
        //given
        Long customerId = 1L;
        Long productId = 1L;

        //when
        boolean result = cartItemDao.isExistItem(customerId, productId);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("특정 사용자나 특정 물건이 존재하지 않으면 false를 반환한다.")
    @Test
    void isExistItemTest_false() {
        //given
        Long customerId = 1L;
        Long productId = 10L;

        //when
        boolean result = cartItemDao.isExistItem(customerId, productId);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("특정 사용자의 특정 물건의 수량을 변경한다.")
    @Test
    void updateQuantityTest() {
        Long customerId = 1L;
        Long productId = 1L;
        int quantity = 10;

        cartItemDao.updateQuantity(customerId, productId, quantity);
        CartItemDto cartItemDto = cartItemDao.findCartItemByCustomerId(customerId)
                .stream()
                .filter(it -> it.getProductId() == productId)
                .findAny()
                .orElseThrow(InvalidCartItemException::new);

        assertThat(cartItemDto.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("특정 사용자id가 주어지면 그 사용자의 카트에 담긴 모든 상품을 삭제한다")
    @Test
    void deleteAllCartItemTest() {
        Long customerId = 1L;

        cartItemDao.deleteAllCartItem(customerId);

        assertThat(cartItemDao.findCartItemByCustomerId(customerId).size()).isEqualTo(0);
    }
}
