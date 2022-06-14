package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.ImageDto;
import woowacourse.shoppingcart.dto.OrderedProduct;
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
    public OrderServiceTest(final  CustomerService customerService, final CartItemService cartItemService,
            final ProductService productService, final OrderService orderService) {
        this.customerService = customerService;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.orderService = orderService;
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
        Long orderId = orderService.addOrder(customer, List.of(cartItemId));

        // then
        OrdersDto ordersDto = orderService.findOrderDetails(customer, orderId);
        List<OrderedProduct> orderedProducts = ordersDto.getOrderedProducts();
        assertThat(orderId).isEqualTo(ordersDto.getId());
        assertThat(orderedProducts.get(0).getName()).isEqualTo("치킨");

        Product product = productService.findById(productId);
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
        Long orderId = orderService.addOrder(customer, List.of(cartItemId, cartItemId1, cartItemId2));

        // then
        OrdersDto ordersDto = orderService.findOrderDetails(customer, orderId);
        List<OrderedProduct> orderedProducts = ordersDto.getOrderedProducts();
        assertThat(orderedProducts).hasSize(3);
    }
}
