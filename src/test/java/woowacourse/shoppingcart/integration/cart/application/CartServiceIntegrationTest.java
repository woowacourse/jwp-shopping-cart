package woowacourse.shoppingcart.integration.cart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.cart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private CustomerDao customerDao;

    private Customer customer;

    @BeforeEach
    void setUp() {
        final Customer customer = new Customer("rick", "rick@gmail.com", "1q2w3e4r");
        final Long customerId = customerDao.save(customer);
        this.customer = new Customer(customerId, customer.getNickname(), customer.getEmail(), customer.getPassword());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCart_newItem_voidReturned() {
        // given
        final Long productId = 1L;

        // when
        final Long actual = cartService.addCart(productId, customer);

        // then
        assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 예외를 던진다.")
    void addCart_notExistProduct_exceptionThrown() {
        // given
        final Long productId = 999L;

        // when, then
        assertThatThrownBy(() -> cartService.addCart(productId, customer))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("이미 장바구니에 담겨있는 상품을 추가하면 예외를 던진다.")
    void addCart_alreadyAddedItem_exceptionThrown() {
        // given
        final Long productId = 1L;

        cartItemDao.addCartItem(customer.getId(), productId);

        // when, then
        assertThatThrownBy(() -> cartService.addCart(productId, customer))
                .isInstanceOf(DuplicateCartItemException.class);
    }
}
