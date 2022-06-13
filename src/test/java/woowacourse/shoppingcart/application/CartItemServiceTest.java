package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ChangeCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

@JdbcTest
@Transactional
@Sql("/data-test.sql")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CartItemServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private CartItemDao cartItemDao;
    private CustomerDao customerDao;
    private ProductDao productDao;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartItemDao = new CartItemDao(jdbcTemplate, namedParameterJdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate, namedParameterJdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        cartService = new CartService(cartItemDao, customerDao, productDao);
    }

    @DisplayName("상품 Id와 LoginCustomer를 입력 받아, 장바구니에 추가한다.")
    @Test
    void addCart() {
        // given
        String email = "beomWhale@naver.com";
        String nickname = "beomWhale";
        String password = "Password1234!";
        Long customerId = createCustomer(email, nickname, password);
        Long productId = createProduct("치킨", 10000, "imageUrl");
        Customer customer = new Customer(customerId, email, nickname, password);

        // when
        Long cartItemId = cartService.addCart(customer, productId);

        // then
        assertThat(cartItemId).isNotNull();
    }

    @DisplayName("LoginCustomer를 입력 받아, 장바구니 리스트를 조회한다.")
    @Test
    void showCartItems() {
        // given
        String email = "beomWhale@naver.com";
        String nickname = "beomWhale";
        String password = "Password1234!";
        Long customerId = createCustomer(email, nickname, password);
        String productName = "치킨";
        Long productId = createProduct(productName, 10000, "imageUrl");
        Customer customer = new Customer(customerId, email, nickname, password);
        cartService.addCart(customer, productId);

        // when
        List<CartItemResponse> cartItemResponses = cartService.showCartItems(customer);

        // then
        CartItemResponse cartItemResponse = cartItemResponses.get(0);
        assertThat(cartItemResponse.getName()).isEqualTo(productName);
    }

    @DisplayName("LoginCustomer, CartItemChangeQuantityRequest, productId를 입력 받아, Customer의 CartItem 수량을 수정한다.")
    @Test
    void changeQuantity() {
        // given
        String email = "beomWhale@naver.com";
        String nickname = "beomWhale";
        String password = "Password1234!";
        Long customerId = createCustomer(email, nickname, password);
        Long productId = createProduct("치킨", 10000, "imageUrl");
        Customer customer = new Customer(customerId, email, nickname, password);
        Long cartItemId = cartService.addCart(customer, productId);

        // when
        int changeQuantity = 10;
        cartService.changeQuantity(customer, new ChangeCartItemQuantityRequest(changeQuantity), productId);
        List<CartItemResponse> cartItemResponses = cartService.showCartItems(customer);

        // then
        CartItemResponse cartItemResponse = cartItemResponses.get(0);
        assertThat(cartItemResponse.getQuantity()).isEqualTo(changeQuantity);
    }

    @DisplayName("장바구니 상품 수량이 음수일 경우, 예외가 발생한다.")
    @Test
    void throwExceptionWhenNegativeQuantity() {
        // given
        String email = "beomWhale@naver.com";
        String nickname = "beomWhale";
        String password = "Password1234!";
        Long customerId = createCustomer(email, nickname, password);
        Long productId = createProduct("치킨", 10000, "imageUrl");
        Customer customer = new Customer(customerId, email, nickname, password);
        Long cartItemId = cartService.addCart(customer, productId);

        // when && then
        int changeQuantity = -1;
        assertThatThrownBy(() ->
          cartService.changeQuantity(customer, new ChangeCartItemQuantityRequest(changeQuantity), productId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("장바구니 상품 수량을 음수로 수정할 수 없습니다.");
    }

    private Long createCustomer(String email, String nickname, String password) {
        Customer customer = new Customer(email, nickname, password);
        return customerDao.save(customer);
    }

    private Long createProduct(String name, int price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        return productDao.save(product);
    }
}
