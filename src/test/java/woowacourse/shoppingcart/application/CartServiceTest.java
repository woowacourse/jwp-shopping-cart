package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.application.AuthService;


@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class CartServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("장바구니에 제품을 추가한다.")
    @Test
    void addCart() {

    }
}
