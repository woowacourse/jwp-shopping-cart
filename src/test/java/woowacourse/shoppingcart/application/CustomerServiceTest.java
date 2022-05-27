package woowacourse.shoppingcart.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CustomerSignUpRequest;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("유저를 저장한다")
    void save() {
        CustomerSignUpRequest request = new CustomerSignUpRequest("username", "password123", "01012345678", "성담빌딩");
        assertDoesNotThrow(() -> customerService.save(request));
    }
}
