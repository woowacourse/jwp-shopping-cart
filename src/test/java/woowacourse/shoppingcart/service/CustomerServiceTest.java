package woowacourse.shoppingcart.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dto.request.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.request.SignUpDto;
import woowacourse.shoppingcart.dto.request.UpdateCustomerDto;
import woowacourse.shoppingcart.dto.response.CustomerDto;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema-reset.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @MockBean
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원을 가입시킨다.")
    void signUp() {

        //given
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트");

        //when
        final Long createdCustomerId = customerService.signUp(signUpDto);
        final CustomerDto savedCustomer = customerService.findCustomerById(createdCustomerId);

        //then
        assertThat(createdCustomerId).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("수정하려는 Customer 정보를 받아서 수정된 Customer 정보를 반환한다.")
    void updateCustomer() {

        //given
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final String changedName = "바뀐이름";
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트");
        final Long createdCustomerId = customerService.signUp(signUpDto);
        final UpdateCustomerDto changeForm = new UpdateCustomerDto(changedName);

        //when
        final CustomerDto changedCustomer = customerService.updateCustomer(createdCustomerId, changeForm);

        //then
        assertThat(changedCustomer.getUsername()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("수정하려는 Customer 정보를 받아서 중복된 이름이면 예외가 발생한다.")
    void updateCustomer_duplicateNameException() {

        //given
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final String duplicateName = "테스트2";
        final SignUpDto signUpDto1 = new SignUpDto("test1@test.com", TEST_PASSWORD, "테스트1");
        final SignUpDto signUpDto2 = new SignUpDto("test2@test.com", TEST_PASSWORD, duplicateName);
        final Long createdCustomerId = customerService.signUp(signUpDto1);
        customerService.signUp(signUpDto2);
        final UpdateCustomerDto changeForm = new UpdateCustomerDto(duplicateName);

        //then
        assertThatThrownBy(() -> customerService.updateCustomer(createdCustomerId, changeForm))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @Test
    @DisplayName("회원 id를 입력받아 회원을 삭제시킨다.")
    void deleteCustomer() {

        //given
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encrypt(any())).thenReturn("encryptedPassword");
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트");
        final Long createdCustomerId = customerService.signUp(signUpDto);
        final DeleteCustomerDto deleteCustomerDto = new DeleteCustomerDto(TEST_PASSWORD);

        //when
        customerService.deleteCustomer(createdCustomerId, deleteCustomerDto);

        //then
        assertThatThrownBy(() -> customerService.findCustomerById(createdCustomerId))
                .isInstanceOf(InvalidCustomerException.class);
    }
}
