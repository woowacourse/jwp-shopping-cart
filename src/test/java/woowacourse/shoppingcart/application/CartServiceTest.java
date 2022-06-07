package woowacourse.shoppingcart.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품을 카트에 저장한다.")
    void saveCardItem() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "rookie"));
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));

        // when & then
        assertDoesNotThrow(() -> cartService.saveCartItem(1L, 1L));
    }
}
