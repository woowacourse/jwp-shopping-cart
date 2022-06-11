package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.utils.JwtTokenProvider;

@SpringBootTest
@Sql({"/clear-all-tables.sql", "/data.sql"})
class AuthServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 정보를 이용해서 토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        customerService.save(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
        TokenRequest request = new TokenRequest("philz@gmail.com", "123456789");
        String token = authService.createToken(request);

        // when
        String email = jwtTokenProvider.restorePayload(token);

        // then
        assertThat(email).isEqualTo("philz@gmail.com");
    }

    @DisplayName("토큰을 이용해서 Customer를 복원한다.")
    @Test
    void findCustomerByToken() {
        // given
        Long savedId = customerService.save(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
        TokenRequest request = new TokenRequest("philz@gmail.com", "123456789");
        String token = authService.createToken(request);

        // when
        Customer customer = authService.findCustomerByToken(token);

        Customer expected = customerDao.findById(savedId).get();
        // then

        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
