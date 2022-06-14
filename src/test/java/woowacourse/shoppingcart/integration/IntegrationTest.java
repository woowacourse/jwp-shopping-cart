package woowacourse.shoppingcart.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.auth.application.AuthService;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.order.application.OrderService;
import woowacourse.shoppingcart.product.application.ProductService;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class IntegrationTest {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected CartService cartService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected CustomerDao customerDao;

    @Autowired
    protected CartItemDao cartItemDao;
}
