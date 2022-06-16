package woowacourse.shoppingcart.unit.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.order.application.OrderService;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundCartItemException;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.unit.ServiceMockTest;

class OrderServiceTest extends ServiceMockTest {

    @InjectMocks
    private OrderService orderService;

    private Customer customer;

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();
        customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
    }

    @Test
    @DisplayName("장바구니에 담긴 상품들의 id에 해당하는 상품을 주문한다.")
    void addOrder_containsAll_orderIdReturned() {
        // given
        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(1L),
                new OrderCreationRequest(3L)
        );

        final Long expectedOrderId = 5L;
        given(orderDao.addOrders(customer.getId()))
                .willReturn(expectedOrderId);

        final List<CartItem> cartItems = List.of(
                new CartItem(1L, new Product(1L, "망고", 1900, "man.go"), 3),
                new CartItem(3L, new Product(3L, "바나나", 1100, "bana.na"), 7),
                new CartItem(2L, new Product(2L, "오렌지", 750, "orange.org"), 2)

        );
        final Cart cart = new Cart(cartItems);
        given(cartService.findCartBy(customer))
                .willReturn(cart);

        // when
        final Long actual = orderService.addOrder(request, customer);

        // then
        assertThat(actual).isEqualTo(expectedOrderId);
    }

    @Test
    @DisplayName("주문 요청 상품 Id가 장바구니에 모두 포함되지 않으면 예외를 던진다.")
    void addOrder_notContainsAll_exceptionThrown() {
        // given
        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(1L),
                new OrderCreationRequest(7L)
        );

        given(orderDao.addOrders(customer.getId()))
                .willReturn(1L);

        final List<CartItem> cartItems = List.of(
                new CartItem(1L, new Product(1L, "망고", 1900, "man.go"), 3),
                new CartItem(3L, new Product(3L, "바나나", 1100, "bana.na"), 7),
                new CartItem(2L, new Product(2L, "오렌지", 750, "orange.org"), 2)
        );
        final Cart cart = new Cart(cartItems);
        given(cartService.findCartBy(customer))
                .willReturn(cart);

        // when, then
        assertThatThrownBy(() -> orderService.addOrder(request, customer))
                .isInstanceOf(NotFoundCartItemException.class);
    }
}
