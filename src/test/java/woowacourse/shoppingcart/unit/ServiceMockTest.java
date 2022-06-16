package woowacourse.shoppingcart.unit;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.order.dao.OrderDao;
import woowacourse.shoppingcart.order.dao.OrdersDetailDao;
import woowacourse.shoppingcart.product.application.ProductService;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.support.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceMockTest {

    protected static final String HASH = "$2a$10$aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    protected static MockedStatic<BCrypt> bcrypt;

    @Mock
    protected CustomerDao customerDao;

    @Mock
    protected CartItemDao cartItemDao;

    @Mock
    protected OrderDao orderDao;

    @Mock
    protected OrdersDetailDao ordersDetailDao;

    @Mock
    protected ProductDao productDao;

    @Mock
    protected CustomerService customerService;

    @Mock
    protected ProductService productService;

    @Mock
    protected CartService cartService;

    @Mock
    protected JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    protected void setUp() {
        bcrypt = mockStatic(BCrypt.class);
    }

    @AfterEach
    void close() {
        bcrypt.close();
    }
}
