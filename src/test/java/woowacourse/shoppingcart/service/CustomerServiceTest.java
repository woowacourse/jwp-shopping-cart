package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignInDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.TokenResponseDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Test
    @DisplayName("회원을 가입시킨다.")
    void signUp() {
        final SignUpDto signUpDto = new SignUpDto("test@test.com", "testtest","테스트");

        final Long createdCustomerId = customerService.signUp(signUpDto);
        final CustomerDto savedCustomer = customerService.findCustomerById(createdCustomerId);

        assertThat(createdCustomerId).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("로그인 정보를 받아서 토큰을 반환한다.")
    void login() {
        final String customerUsername = "테스트";
        final SignUpDto signUpDto = new SignUpDto("test@test.com", "testtest", customerUsername);
        customerService.signUp(signUpDto);

        final SignInDto signInDto = new SignInDto("test@test.com", "testtest");
        final TokenResponseDto token = customerService.login(signInDto);

        final String payload = jwtTokenProvider.getPayload(token.getAccessToken());

        assertThat(payload).isEqualTo(customerUsername);
    }

    @Test
    @DisplayName("수정하려는 Customer 정보를 받아서 수정된 Customer 정보를 반환한다.")
    void updateCustomer() {
        final String changedName = "바뀐이름";
        final SignUpDto signUpDto = new SignUpDto("test@test.com", "testtest","테스트");
        final Long createdCustomerId = customerService.signUp(signUpDto);
        final UpdateCustomerDto changeForm = new UpdateCustomerDto(changedName);

        final CustomerDto changedCustomer = customerService.updateCustomer(createdCustomerId, changeForm);

        assertThat(changedCustomer.getUsername()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("수정하려는 Customer 정보를 받아서 중복된 이름이면 예외가 발생한다.")
    void updateCustomer_duplicateNameException() {
        final String duplicateName = "테스트2";
        final SignUpDto signUpDto1 = new SignUpDto("test1@test.com", "testtest","테스트1");
        final SignUpDto signUpDto2 = new SignUpDto("test2@test.com", "testtest",duplicateName);
        final Long createdCustomerId = customerService.signUp(signUpDto1);
        customerService.signUp(signUpDto2);
        final UpdateCustomerDto changeForm = new UpdateCustomerDto(duplicateName);

        assertThatThrownBy(() -> customerService.updateCustomer(createdCustomerId, changeForm))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessageContaining("수정하려는 이름이 이미 존재합니다.");
    }

    @Test
    @DisplayName("회원 id를 입력받아 회원을 삭제시킨다.")
    void deleteCustomer() {
        final SignUpDto signUpDto = new SignUpDto("test@test.com", "testtest","테스트");
        final Long createdCustomerId = customerService.signUp(signUpDto);

        customerService.deleteCustomer(createdCustomerId);
        assertThatThrownBy(() -> customerService.findCustomerById(createdCustomerId))
                .isInstanceOf(InvalidCustomerException.class);
    }
}