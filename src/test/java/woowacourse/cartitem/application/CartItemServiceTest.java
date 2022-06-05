package woowacourse.cartitem.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.customer.application.CustomerService;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.product.application.ProductService;
import woowacourse.product.dto.ProductRequest;

@Transactional
@SpringBootTest
public class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    private String username;
    private Long productId1;
    private Long productId2;

    @BeforeEach
    void setUp() {
        username = customerService.save(new SignupRequest("username", "password", "01000001111", "서울시")).getUsername().getValue();
        productId1 = productService.addProduct(new ProductRequest("짱구", 100_000_000, 1, "jjanggu.jpg"));
        productId2 = productService.addProduct(new ProductRequest("짱아", 10_000_000, 1, "jjanga.jpg"));
    }

    @DisplayName("카트에 아이템을 추가한다.")
    @Test
    void addCartItem() {
        final CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(productId1, 1);

        final Long cartItemId = cartItemService.addCartItem(username, cartItemAddRequest);

        assertThat(cartItemId).isNotNull();
    }
}
