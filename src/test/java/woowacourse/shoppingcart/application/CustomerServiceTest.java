package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdateRequest;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("회원을 저장한다.")
    void saveCustomer() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");

        // when
        Long customerId = customerService.save(request);

        // then
        CustomerResponse response = customerService.findById(customerId);
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new CustomerResponse(1L, "email@email.com", "rookie"));
    }

    @Test
    @DisplayName("이미 등록된 이메일로 가입할 경우 예외가 발생한다.")
    void saveCustomerDuplicateEmail() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when & then
        assertThatThrownBy(() -> customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "zero")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미 등록된 닉네임으로 가입할 경우 예외가 발생한다.")
    void saveCustomerDuplicateNickname() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email1@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when & then
        assertThatThrownBy(() -> customerService.save(new CustomerSaveRequest("email2@email.com", "password1234A!", "rookie")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "pas12A!", "passwordPASSWORD1234!", "password123!", "PASSWORD123!", "password123A", "passwordA!"})
    @DisplayName("패스워드는 8자 이상 20자 이하, 대소문자, 특수문자, 숫자 하나이상을 포함하지 않으면 예외가 발생한다.")
    void wrongPasswordSaveCustomer(String password) {
        assertThatThrownBy(() -> customerService.save(new CustomerSaveRequest("email2@email.com", password, "rookie")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("id 값으로 회원을 조회한다.")
    void findById() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");
        Long customerId = customerService.save(request);

        // when
        CustomerResponse customerResponse = customerService.findById(customerId);

        // then
        assertThat(customerResponse).isEqualTo(new CustomerResponse(1L, "email@email.com", "rookie"));
    }

    @Test
    @DisplayName("id 값이 존재하지 않는 회원을 조회할 경우 예외가 발생한다.")
    void findByNotExistedId() {
        // given & when & then
        assertThatThrownBy(() -> customerService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("회원 정보를 수정할 수 있다.")
    void update() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when
        customerService.update(1L, new CustomerUpdateRequest("zero"));

        // then
        CustomerResponse customerResponse = customerService.findById(1L);
        assertThat(customerResponse).isEqualTo(new CustomerResponse(1L, "email@email.com", "zero"));
    }

    @Test
    @DisplayName("회원 비밀번호를 수정할 수 있다.")
    void updatePassword() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when & then
        assertDoesNotThrow(() -> customerService.updatePassword(1L, new CustomerUpdatePasswordRequest("password1234A!", "password1234A@")));
    }

    @Test
    @DisplayName("id 값이 존재하지 않는 회원을 변경할 경우 예외가 발생한다.")
    void updateByNotExistedId() {
        // given & when & then
        assertThatThrownBy(() -> customerService.update(1L, new CustomerUpdateRequest("rookie")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("회원을 삭제할 수 있다.")
    void delete() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when & then
        assertDoesNotThrow(() -> customerService.delete(1L));
    }

    @Test
    @DisplayName("회원이 없는 경우 삭제시 예외가 발생한다.")
    void deleteByNotExistedId() {
        // when & then
        assertThatThrownBy(() -> customerService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
