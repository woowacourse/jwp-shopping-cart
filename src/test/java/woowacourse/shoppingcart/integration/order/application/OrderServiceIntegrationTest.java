package woowacourse.shoppingcart.integration.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.integration.IntegrationTest;
import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.order.domain.Orders;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundCartItemException;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundOrderException;
import woowacourse.shoppingcart.product.domain.Product;

class OrderServiceIntegrationTest extends IntegrationTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        final Customer customer = new Customer("rick", "rick@gmail.com", "1q2w3e4r");
        final Long id = customerDao.save(customer);
        this.customer = new Customer(id, customer.getNickname(), customer.getEmail(), customer.getPassword());
    }

    @Test
    @DisplayName("주문하려는 상품의 id들이 모두 장바구니에 담겨있으면 주문을 생성한다.")
    void addOrder_containsAll_orderIdReturned() {
        // given
        cartItemDao.addCartItem(customer.getId(), 1L);
        cartItemDao.addCartItem(customer.getId(), 2L);
        cartItemDao.addCartItem(customer.getId(), 3L);

        final List<OrderCreationRequest> requests = List.of(
                new OrderCreationRequest(1L),
                new OrderCreationRequest(3L)
        );

        // when
        final Long actual = orderService.addOrder(requests, customer);
        final List<Long> productIds = cartItemDao.findCartByCustomerId(customer.getId())
                .getValues()
                .stream()
                .map(CartItem::getProduct)
                .map(Product::getId)
                .collect(Collectors.toList());

        // then
        assertThat(actual).isNotNull();
        assertThat(productIds).containsExactly(2L);
    }

    @Test
    @DisplayName("주문하려는 상품의 id들 중에 장바구니에 담겨있지 않는 상품이 존재하면 예외를 던진다.")
    void addOrder_notContainsAll_exceptionThrown() {
        // given
        cartItemDao.addCartItem(customer.getId(), 1L);
        cartItemDao.addCartItem(customer.getId(), 2L);

        final List<OrderCreationRequest> requests = List.of(
                new OrderCreationRequest(1L),
                new OrderCreationRequest(4L)
        );

        // when, then
        assertThatThrownBy(() -> orderService.addOrder(requests, customer))
                .isInstanceOf(NotFoundCartItemException.class);
    }

    @Test
    @DisplayName("Order의 Id에 해당하는 Order를 조회한다.")
    void findOrderById() {
        // given
        cartItemDao.addCartItem(customer.getId(), 1L);
        cartItemDao.addCartItem(customer.getId(), 2L);
        cartItemDao.addCartItem(customer.getId(), 3L);

        final List<OrderCreationRequest> requests = List.of(
                new OrderCreationRequest(1L),
                new OrderCreationRequest(3L)
        );
        final Long orderId = orderService.addOrder(requests, customer);

        // when
        final Orders orders = orderService.findOrderById(customer, orderId);
        final List<Long> actualProductIds = orders.getOrderDetails()
                .stream()
                .map(OrderDetail::getProduct)
                .map(Product::getId)
                .collect(Collectors.toList());
        final List<Integer> actualQuantities = orders.getOrderDetails()
                .stream()
                .map(OrderDetail::getQuantity)
                .collect(Collectors.toList());

        // then
        assertThat(actualProductIds).containsExactly(1L, 3L);
        assertThat(actualQuantities).containsExactly(1, 1);
    }

    @Test
    @DisplayName("Order의 Id에 해당하는 Order가 존재하지 않으면 예외를 던진다.")
    void findOrderById_notExistOrder_exceptionThrown() {
        // when, then
        assertThatThrownBy(() -> orderService.findOrderById(customer, 999L))
                .isInstanceOf(NotFoundOrderException.class);
    }

    @Test
    @DisplayName("Customer의 모든 Order를 조회한다.")
    void findAllOrders() {
        // given
        cartItemDao.addCartItem(customer.getId(), 1L);
        cartItemDao.addCartItem(customer.getId(), 2L);
        cartItemDao.addCartItem(customer.getId(), 3L);

        final List<OrderCreationRequest> firstOrderRequest = List.of(
                new OrderCreationRequest(1L),
                new OrderCreationRequest(3L)
        );
        final Long firstOrderId = orderService.addOrder(firstOrderRequest, customer);

        final List<OrderCreationRequest> secondOrderRequest = List.of(
                new OrderCreationRequest(2L)
        );
        final Long secondOrderId = orderService.addOrder(secondOrderRequest, customer);

        // when
        final List<Orders> actual = orderService.findAllOrders(customer);
        final List<Long> actualOrderIds = actual.stream()
                .map(Orders::getId)
                .collect(Collectors.toList());

        // then
        assertThat(actualOrderIds).containsExactly(firstOrderId, secondOrderId);
    }
}
