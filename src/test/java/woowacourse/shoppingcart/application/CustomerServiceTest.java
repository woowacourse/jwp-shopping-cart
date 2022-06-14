package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.fixture.CustomerFixture.connieDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.exception.BadRequestException;
import woowacourse.auth.exception.NotFoundException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
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
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("이미 가입한 사용자입니다.");
        }
    }


    @DisplayName("회원 조회를 할 때")
    @Nested
    class CustomerFindTest {

        @DisplayName("유효한 회원이면 데이터를 반환한다.")
        @Test
        void foundCustomer() {
            Long 코니_id = 코니_회원_가입();
            CustomerResponse response = 회원_조회(코니_id);
            assertThat(response.getId()).isEqualTo(코니_id);
        }

        @Test
        @DisplayName("회원 가입을 하지 않은 유저면 에러가 발생한다.")
        void noSignUpCustomer() {
            assertThatThrownBy(() -> customerService.findCustomerById(111L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("회원을 조회할 수 없습니다.");
        }
    }

    @DisplayName("회원 수정을 할 때")
    @Nested
    class UpdateTest {

        @DisplayName("유효한 회원과 데이터가 들어오면, 업데이트 한다.")
        @Test
        void updateCustomer() {

            Long 코니_id = 코니_회원_가입();
            ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "password1!",
                    "example.com", "코니", "male", "1988-08-07",
                    "01987654321",
                    new AddressRequest("d", "e", "54321"), true);
            assertThatNoException().isThrownBy(() -> customerService.updateCustomer(코니_id, modifiedCustomerDto));
        }

        @DisplayName("Email 은 업데이트가 되지 않는다.")
        @Test
        void noUpdateCauseEmail() {
            Long 코니_id = 코니_회원_가입();

            ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("connie@naver.com", "password1!",
                    "example.com", "코니", "female", "1988-08-07",
                    "01987654321",
                    new AddressRequest("d", "e", "54321"), true);
            customerService.updateCustomer(코니_id, modifiedCustomerDto);
            CustomerResponse response = 회원_조회(코니_id);

            assertThat(response.getEmail()).isEqualTo(connieDto.getEmail());
        }

        @DisplayName("유효하지 않은 PW 는 업데이트가 되지 않는다.")
        @Test
        void noUpdateCausePw() {
            Long 코니_id = 코니_회원_가입();

            ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "유효하지않음",
                    "example.com", "코니", "female", "1988-08-07",
                    "01987654321",
                    new AddressRequest("d", "e", "54321"), true);

            assertThatThrownBy(() -> customerService.updateCustomer(코니_id, modifiedCustomerDto))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("비밀번호는 8글자 이상 20글자 이하, 영문, 특수문자, 숫자 최소 1개씩 으로 이뤄져야합니다.");
        }

        @DisplayName("유효하지 않은 name 는 업데이트가 되지 않는다.")
        @Test
        void noUpdateCauseName() {
            Long 코니_id = 코니_회원_가입();

            ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "password1!",
                    "example.com", "connie", "female", "1988-08-07",
                    "01987654321",
                    new AddressRequest("d", "e", "54321"), true);

            assertThatThrownBy(() -> customerService.updateCustomer(코니_id, modifiedCustomerDto))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("이름은 최대 5글자, 한글로 이뤄져야합니다.");
        }

        @DisplayName("유효하지 않은 birthday 는 업데이트가 되지 않는다.")
        @Test
        void noUpdateCauseBirthday() {
            Long 코니_id = 코니_회원_가입();

            ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "password1!",
                    "example.com", "코니", "female", "11111-08-07",
                    "01987654321",
                    new AddressRequest("d", "e", "54321"), true);

            assertThatThrownBy(() -> customerService.updateCustomer(코니_id, modifiedCustomerDto))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("올바르지 않은 생년월일입니다.");
        }

        @DisplayName("유효하지 않은 Contact 는 업데이트가 되지 않는다.")
        @Test
        void noUpdateCauseContact() {
            Long 코니_id = 코니_회원_가입();

            ModifiedCustomerDto modifiedCustomerDto = new ModifiedCustomerDto("her0807@naver.com", "password1!",
                    "example.com", "코니", "female", "1988-08-07",
                    "123456789101",
                    new AddressRequest("d", "e", "54321"), true);

            assertThatThrownBy(() -> customerService.updateCustomer(코니_id, modifiedCustomerDto))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("핸드폰 양식의 전화번호를 입력해야합니다.");
        }
    }

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
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("삭제가 되지 않았습니다.");
        }

    }

    private Long 코니_회원_가입() {
        CustomerDto newCustomer = connieDto;
        final Long customerId = customerService.createCustomer(newCustomer);
        return customerId;
    }

    private CustomerResponse 회원_조회(Long id) {
        CustomerResponse response = customerService.findCustomerById(id);
        return response;
    }
}
