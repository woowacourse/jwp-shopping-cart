package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.dto.ImageDto;
import woowacourse.shoppingcart.dto.OrderedProducts;
import woowacourse.shoppingcart.dto.OrdersDto;
import woowacourse.shoppingcart.dto.ProductRequest;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class OrderServiceTest {

    private CustomerService customerService;
    private CartItemService cartItemService;
    private ProductService productService;
    private OrderService orderService;

    private Customer customer;
    private Long productId;
    private Long cartItemId;

    @Autowired
    public OrderServiceTest(final JdbcTemplate jdbcTemplate) {
        this.customerService = new CustomerService(new CustomerDao(jdbcTemplate));
        this.cartItemService = new CartItemService(new CartItemDao(jdbcTemplate),
                new ProductService(new ProductDao(jdbcTemplate)));
        this.productService = new ProductService(new ProductDao(jdbcTemplate));
        this.orderService = new OrderService(new OrderDao(jdbcTemplate), new OrdersDetailDao(jdbcTemplate),
                productService, customerService, cartItemService);
    }

    @BeforeEach
    void setUp() {
        customer = customerService.register("test@gmail.com", "password0!", "루나");
        productId = productService.addProduct(new ProductRequest("치킨", 10_000,
                20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        cartItemId = cartItemService.addCart(customer, productId);
    }

    @DisplayName("하나의 주문을 추가한다.")
    @Test
    void addOrder() {
        // when
        Long orderId = orderService.addOrder(customer, new CartItemIds(List.of(cartItemId)));

        // then
        OrdersDto ordersDto = orderService.findOrderByCustomerAndOrderId(customer, orderId);
        List<OrderedProducts> orderedProducts = ordersDto.getOrderedProducts();
        assertThat(orderId).isEqualTo(ordersDto.getId());
        assertThat(orderedProducts.get(0).getName()).isEqualTo("치킨");

        Product product = productService.findProductById(productId);
        assertThat(product.getStockQuantity()).isEqualTo(19);
    }

    @DisplayName("여러 개의 주문을 추가한다.")
    @Test
    void addOrders() {
        // given
        Long productId1 = productService.addProduct(new ProductRequest("피자", 20_000,
                20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        Long cartItemId1 = cartItemService.addCart(customer, productId1);
        Long productId2 = productService.addProduct(new ProductRequest("콜라", 15_000,
                20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        Long cartItemId2 = cartItemService.addCart(customer, productId2);

        // when
        Long orderId = orderService.addOrder(customer, new CartItemIds(List.of(cartItemId, cartItemId1, cartItemId2)));

        // then
        OrdersDto ordersDto = orderService.findOrderByCustomerAndOrderId(customer, orderId);
        List<OrderedProducts> orderedProducts = ordersDto.getOrderedProducts();
        assertThat(orderedProducts).hasSize(3);
    }
}
