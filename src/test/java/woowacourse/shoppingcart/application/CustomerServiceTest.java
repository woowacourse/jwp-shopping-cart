package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.TokenPayloadDto;
import woowacourse.shoppingcart.dao.CustomerFixture;

@SpringBootTest
@DisplayName("CustomerService 는")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

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

    @Test
    @DisplayName("회원가입을 할때 회원가입이 정상적으로 이뤄진다.")
    void createCustomer() {
        final Long customerId = signUpCustomer();
        assertThat(customerId).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("로그인이 되면 토큰이 정상적으로 발급된다.")
    void createAccessToken() throws JsonProcessingException {
        signUpCustomer();
        final String email = "her0807@naver.com";
        final TokenResponse response = customerService.signIn(new TokenRequest(email, "password1!"));
        final ObjectMapper mapper = new JsonMapper();
        TokenPayloadDto tokenPayloadDto = mapper.readValue(tokenProvider.getPayload(response.getAccessToken()),
                TokenPayloadDto.class);
        assertThat(tokenPayloadDto.getEmail()).isEqualTo(email);
    }

    private Long signUpCustomer() {
        CustomerDto newCustomer = CustomerFixture.tommyDto;
        final Long customerId = customerService.createCustomer(newCustomer);
        return customerId;
    }
}
