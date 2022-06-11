package woowacourse.shoppingcart.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원을 가입시킨다.")
    void signUp() {
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트");

        final Long createdCustomerId = customerService.signUp(signUpDto);
        final CustomerDto savedCustomer = customerService.findCustomerById(createdCustomerId);

        assertThat(createdCustomerId).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("수정하려는 Customer 정보를 받아서 수정된 Customer 정보를 반환한다.")
    void updateCustomer() {
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final String changedName = "바뀐이름";
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트");
        final Long createdCustomerId = customerService.signUp(signUpDto);
        final UpdateCustomerDto changeForm = new UpdateCustomerDto(changedName);

        final CustomerDto changedCustomer = customerService.updateCustomer(createdCustomerId, changeForm);

        assertThat(changedCustomer.getUsername()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("수정하려는 Customer 정보를 받아서 중복된 이름이면 예외가 발생한다.")
    void updateCustomer_duplicateNameException() {
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final String duplicateName = "테스트2";
        final SignUpDto signUpDto1 = new SignUpDto("test1@test.com", TEST_PASSWORD, "테스트1");
        final SignUpDto signUpDto2 = new SignUpDto("test2@test.com", TEST_PASSWORD, duplicateName);
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
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트");
        final Long createdCustomerId = customerService.signUp(signUpDto);
        final DeleteCustomerDto deleteCustomerDto = new DeleteCustomerDto(TEST_PASSWORD);

        customerService.deleteCustomer(createdCustomerId, deleteCustomerDto);
        assertThatThrownBy(() -> customerService.findCustomerById(createdCustomerId))
                .isInstanceOf(InvalidCustomerException.class);
    }
}