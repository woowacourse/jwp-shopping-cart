package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.application.CustomerServiceTest.ADDRESS;
import static woowacourse.shoppingcart.application.CustomerServiceTest.PASSWORD;
import static woowacourse.shoppingcart.application.CustomerServiceTest.PHONE_NUMBER;
import static woowacourse.shoppingcart.application.CustomerServiceTest.USERNAME;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.PlainPassword;
import woowacourse.shoppingcart.dto.cartitem.CartItemSaveRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;

@SpringBootTest
@Transactional
class CartItemServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartItemService cartItemService;

    private Long productId;

    @BeforeEach
    void setUp() {
        customerDao.save(Customer.builder()
                .username(USERNAME)
                .password(passwordEncoder.encode(new PlainPassword(PASSWORD)))
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .build());
        productId = productDao.save(new Product("치킨", 10000, 10, "url"));
    }

    @Test
    @DisplayName("카트에 담을 때 존재 수량보다 크게 담을 경우 예외 발생")
    void addCartLargePurchaseQuantity_throwException() {
        CartItemSaveRequest request = new CartItemSaveRequest(productId, 11);
        assertThatThrownBy(() -> cartItemService.addCart(request, USERNAME))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("제품의 수량보다 더 주문할 수 없습니다.");
    }

    @Test
    @DisplayName("정상적으로 카트 추가")
    void addCart() {
        CartItemSaveRequest request = new CartItemSaveRequest(productId, 10);
        assertThat(cartItemService.addCart(request, USERNAME)).isNotNull();
    }
}
