package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.AddressResponse;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.dao.CustomerFixture;
import woowacourse.shoppingcart.dto.request.AddressRequest;

@SpringBootTest
@DisplayName("CustomerService 는")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=0");
        jdbcTemplate.update("truncate table orders_detail");
        jdbcTemplate.update("truncate table cart_item");
        jdbcTemplate.update("truncate table orders");
        jdbcTemplate.update("truncate table product");
        jdbcTemplate.update("truncate table customer");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=1");
    }

    @DisplayName("회원가입을 할 때")
    @Nested
    class SignInTest {

        @Test
        @DisplayName("첫 회원가입이면 회원가입이 정상적으로 이뤄진다.")
        void createCustomer() {
            final Long customerId = signUpCustomer();
            assertThat(customerId).isGreaterThanOrEqualTo(1);
        }

        @Test
        @DisplayName("이미 가입된 회원이면 에러를 던진다.")
        void duplicatedCustomer() {
            signUpCustomer();
            assertThatThrownBy(CustomerServiceTest.this::signUpCustomer)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 가입한 사용자입니다.");
        }
    }

    @Test
    @DisplayName("로그인이 되면 토큰이 정상적으로 발급된다.")
    void createAccessToken() throws JsonProcessingException {
        signUpCustomer();
        final String email = "her0807@naver.com";
        final TokenResponse response = authService.signIn(new SignInDto(email, "password1!"));
        final ObjectMapper mapper = new JsonMapper();
        AddressResponse tokenPayloadDto = mapper.readValue(tokenProvider.getPayload(response.getAccessToken()),
                AddressResponse.class);
        assertThat(tokenPayloadDto.getEmail()).isEqualTo(email);
        assertThat(response.getUserId()).isNotNull();
    }

    private Long signUpCustomer() {
        CustomerDto newCustomer = CustomerFixture.tommyDto;
        final Long customerId = customerService.createCustomer(newCustomer);
        return customerId;
    }

    @DisplayName("사용자 정보를 업데이트 한다.")
    @Test
    void updateCustomer() {
        signUpCustomer();
        ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "password1!",
                "example.com", "토미", "male", "1988-08-07",
                "01987654321",
                new AddressRequest("d", "e", "54321"), true);
        assertThatNoException().isThrownBy(() -> customerService.updateCustomer(modifiedCustomerDto));
    }
}
