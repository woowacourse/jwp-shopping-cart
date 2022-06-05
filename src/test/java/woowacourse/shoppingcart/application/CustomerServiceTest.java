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
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.AddressResponse;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.dao.CustomerFixture;
import woowacourse.shoppingcart.dto.AddressRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

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

    @DisplayName("회원가입을 할 때")
    @Nested
    class SignUpTest {

        @Test
        @DisplayName("첫 회원가입이면 회원가입이 정상적으로 이뤄진다.")
        void createCustomer() {
            final Long customerId = 코니_회원_가입();
            assertThat(customerId).isGreaterThanOrEqualTo(1);
        }

        @Test
        @DisplayName("이미 가입된 회원이면 에러를 던진다.")
        void duplicatedCustomer() {
            코니_회원_가입();
            assertThatThrownBy(() -> 코니_회원_가입())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 가입한 사용자입니다.");
        }
    }

    @DisplayName("로그인을 할 때")
    @Nested
    class SignInTest {

        @Test
        @DisplayName("로그인이 되면 토큰이 정상적으로 발급된다.")
        void createAccessToken() throws JsonProcessingException {
            코니_회원_가입();
            final String email = "her0807@naver.com";
            final TokenResponse response = customerService.signIn(new SignInDto(email, "password1!"));
            final ObjectMapper mapper = new JsonMapper();
            AddressResponse tokenPayloadDto = mapper.readValue(tokenProvider.getPayload(response.getAccessToken()),
                    AddressResponse.class);
            assertThat(tokenPayloadDto.getEmail()).isEqualTo(email);
            assertThat(response.getCustomerId()).isNotNull();
        }

        @Test
        @DisplayName("회원 가입을 하지 않은 유저면 에러가 발생한다.")
        void noSignUpCustomer() {
            final String email = "her0807@naver.com";
            assertThatThrownBy(() -> customerService.signIn(new SignInDto(email, "password1!")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("가입하지 않은 유저입니다.");
        }
    }

    @DisplayName("회원 조회를 할 때")
    @Nested
    class CustomerFindTest {

        @DisplayName("유효한 화원이면 데이터를 반환한다.")
        @Test
        void foundCustomer() {
            Long 코니_id = 코니_회원_가입();
            CustomerResponse response = customerService.findCustomerById(코니_id);
            assertThat(response.getId()).isEqualTo(코니_id);
        }

        @Test
        @DisplayName("회원 가입을 하지 않은 유저면 에러가 발생한다.")
        void noSignUpCustomer() {
            assertThatThrownBy(() -> customerService.findCustomerById(111L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("회원을 조회할 수 없습니다.");
        }
    }

    //:todo 유효하지 않은 케이스 전부 검증

    @DisplayName("사용자 정보를 업데이트 한다.")
    @Test
    void updateCustomer() {
        Long 코니_id = 코니_회원_가입();
        ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "password1!",
                "example.com", "코니", "male", "1988-08-07",
                "01987654321",
                new AddressRequest("d", "e", "54321"), true);
        assertThatNoException().isThrownBy(() -> customerService.updateCustomer(코니_id, modifiedCustomerDto));
    }

    @DisplayName("회원 삭제를 할 때")
    @Nested
    class DeleteTest {

        @DisplayName("유효한 화원이면 삭제한다.")
        @Test
        void deleteCustomer() {
            Long 코니_id = 코니_회원_가입();
            assertThatNoException().isThrownBy(() -> customerService.deleteCustomer(코니_id));
        }

        @Test
        @DisplayName("회원 가입을 하지 않은 유저면 에러가 발생한다.")
        void noDeleteCustomer() {
            assertThatThrownBy(() -> customerService.deleteCustomer(111L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("삭제가 되지 않았습니다.");
        }
    }

    private Long 코니_회원_가입() {
        CustomerDto newCustomer = CustomerFixture.tommyDto;
        final Long customerId = customerService.createCustomer(newCustomer);
        return customerId;
    }
}
