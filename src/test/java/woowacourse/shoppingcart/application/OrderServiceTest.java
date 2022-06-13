package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@Sql("/test_schema.sql")
class OrderServiceTest {
    private final CustomerRequest customerRequest =
            new CustomerRequest("kth990303", "kth@@123", "케이", 23);
    private final ProductRequest productRequest1 =
            new ProductRequest("감자1", 200, "woowaPotato.com");
    private final ProductRequest productRequest2 =
            new ProductRequest("감자2", 400, "woowaPotato2.com");
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrdersDetailDao ordersDetailDao;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void init() {
        customerService.addCustomer(customerRequest);

        Long product1Id = productService.addProduct(productRequest1);
        productResponse1 = ProductResponse.of(product1Id, productRequest1);

        Long product2Id = productService.addProduct(productRequest2);
        productResponse2 = ProductResponse.of(product2Id, productRequest2);

        cartService.addCart(product1Id, "kth990303");
        cartService.addCart(product2Id, "kth990303");
    }

    @DisplayName("장바구니 상품들을 주문에 담는다.")
    @Test
    void addOrder() {
        List<Long> productIds = List.of(productResponse1.getId(), productResponse2.getId());
        Long orderId = orderService.addOrder(productIds, "kth990303");

        List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);

        assertThat(orderDetails.size()).isEqualTo(2);
    }

    @DisplayName("장바구니에 존재하지 않는 상품을 주문에 담으려할 경우 예외를 발생시킨다.")
    @Test
    void addOrder_notExistItemsInCart() {
        List<Long> invalidProductIds = List.of(productResponse1.getId() + 100L, productResponse2.getId() + 100L);

        assertThatExceptionOfType(InvalidCartItemException.class)
                .isThrownBy(() -> orderService.addOrder(invalidProductIds, "kth990303"))
                .withMessageContaining("유효");
    }
}
