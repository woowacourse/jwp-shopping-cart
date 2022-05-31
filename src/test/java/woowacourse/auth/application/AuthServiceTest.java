package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("회원을 등록한다.")
    void saveCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        CustomerResponse response = authService.save(customer);

        assertThat(response).extracting("email", "name", "phone", "address")
                .containsExactly("email", "name", "010-2222-3333", "address");
    }

    @Test
    @DisplayName("중복되는 email이면 예외가 발생한다.")
    void existEmailException() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        authService.save(customer);
        assertThatThrownBy(() -> authService.save(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 email 입니다.");
    }
}
