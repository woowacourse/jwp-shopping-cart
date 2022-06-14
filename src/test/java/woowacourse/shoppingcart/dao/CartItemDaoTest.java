package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CartEntity;
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

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com", 100));
        productDao.save(new Product("apple", 2_000, "woowa2.com", 100));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 1);
    }

    @DisplayName("고객 아이디를 넣으면, 해당 고객이 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("고객 Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("장바구니 Id를 넣으면, 해당 상품 Id를 가져온다.")
    @Test
    void findProductIdById() {

        // given
        final Long cartId = 1L;

        // when
        final Long productId = cartItemDao.findProductIdById(cartId);

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 장바구니 Id를 넣으면 예외가 발생한다.")
    @Test
    void findProductIdByIdFail() {

        // given
        final Long cartId = 3L;

        // when
        assertThatThrownBy(() -> cartItemDao.findProductIdById(cartId))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("장바구니 Id를 넣으면, 해당 상품의 수량을 가져온다.")
    @Test
    void findProductQuantityById() {

        // given
        final Long cartId = 1L;

        // when
        final int quantity = cartItemDao.findProductQuantityById(cartId);

        // then
        assertThat(quantity).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 장바구니 Id를 넣으면 예외가 발생한다.")
    @Test
    void findProductQuantityByIdFail() {

        // given
        final Long cartId = 3L;

        // when
        assertThatThrownBy(() -> cartItemDao.findProductQuantityById(cartId))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("고객 Id 와 상품 Id 를 통해 수량을 찾아낸다.")
    @Test
    void findQuantityByCustomerIdAndProductId() {

        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final int quantity = cartItemDao.findQuantityByCustomerIdAndProductId(customerId, productId)
                .get()
                .getQuantity();

        // then
        assertThat(quantity).isEqualTo(1);
    }

    @DisplayName("고객 Id 와 상품 Id 를 통해 수량을 찾을 수 없을 경우 빈 값을 반환한다.")
    @Test
    void findQuantityByCustomerIdAndProductIdEmpty() {

        // given
        final Long customerId = 1L;
        final Long productId = 3L;

        // when
        Optional<CartEntity> quantity = cartItemDao.findQuantityByCustomerIdAndProductId(customerId, productId);

        // then
        assertThat(quantity).isEmpty();
    }

    @DisplayName("장바구니에 상품을 담으면, 담긴 장바구니 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        final int quantity = 1;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, quantity);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("장바구니 Id를 통해 장바구니 수량을 수정한다.")
    @Test
    void modifyQuantity() {

        // given
        final Long cartId = 1L;
        final Long productId = 1L;
        final int quantity = 3;

        // when
        cartItemDao.modifyQuantity(cartId, productId, quantity);

        // then
        int actual = cartItemDao.findProductQuantityById(cartId);

        assertThat(actual).isEqualTo(3);
    }

    @DisplayName("존재하지 않는 장바구니 Id를 통해 장바구니 수량을 수정할 경우 예외가 발생한다.")
    @Test
    void modifyQuantityFail() {

        // given
        final Long invalidCartId = 3L;
        final Long invalidProductId = 3L;

        final Long cartId = 1L;
        final Long productId = 1L;
        final int quantity = 3;

        // when
        assertAll(
                () -> assertThatThrownBy(() -> cartItemDao.modifyQuantity(invalidCartId, productId, quantity))
                        .isInstanceOf(InvalidCartItemException.class),
                () -> assertThatThrownBy(() -> cartItemDao.modifyQuantity(cartId, invalidProductId, quantity))
                        .isInstanceOf(InvalidCartItemException.class),
                () -> assertThatThrownBy(() -> cartItemDao.modifyQuantity(invalidCartId, invalidProductId, quantity))
                        .isInstanceOf(InvalidCartItemException.class)
        );
    }

    @DisplayName("장바구니 Id를 통해 장바구니 항목을 제거한다.")
    @Test
    void deleteCartItem() {

        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItem(1L, cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @DisplayName("존재하지 않는 장바구니 Id를 통해 장바구니 항목을 제거할 경우 예외가 발생한다.")
    @Test
    void deleteCartItemFail() {

        // given
        final Long cartId = 3L;

        // when
        assertThatThrownBy(() -> cartItemDao.deleteCartItem(1L, cartId))
                .isInstanceOf(InvalidCartItemException.class);
    }
}
