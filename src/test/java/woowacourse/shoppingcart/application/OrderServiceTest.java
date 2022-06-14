package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.OrderException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.dto.ProductRequest;

@SpringBootTest
@Sql("classpath:truncate.sql")
class OrderServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;


    @BeforeEach
    void setUp() {
        Customer customer = new Customer("test@email.com", Password.fromPlainInput("password0!"), "name");
        customerDao.save(customer);
    }

    @Test
    @DisplayName("존재하지 않는 장바구니 내용일 경우 에러를 발생시킨다.")
    void addOrderFailByNotExistProduct() {
        assertThatThrownBy(() -> orderService.addOrder(List.of(1L), "test@email.com"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("재고보다 많은 양을 주문하려고 할 경우 에러를 발생시킨다.")
    void addOrderFailByQuantity() {
        //given
        ThumbnailImage image = new ThumbnailImage("url", "alt");
        ProductRequest productRequest = new ProductRequest("name", 1000, 10, image);
        productService.addProduct(productRequest);

        cartService.addCart(1L, 11, "test@email.com");

        //then
        assertThatThrownBy(() -> orderService.addOrder(List.of(1L), "test@email.com"))
                .isInstanceOf(OrderException.class);
    }

    @Test
    @DisplayName("존재하지 않는 주문을 조회할 경우 에러를 발생시킨다.")
    void findOrderByIdFail() {
        assertThatThrownBy(() -> orderService.findOrderById("test@email.com", 1L))
                .isInstanceOf(NotFoundException.class);
    }
}