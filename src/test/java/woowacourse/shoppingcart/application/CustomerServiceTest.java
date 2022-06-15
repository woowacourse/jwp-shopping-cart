package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;
import static woowacourse.ShoppingCartFixture.잉_회원탈퇴요청;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.badRequest.EmailDuplicateException;
import woowacourse.exception.unauthorization.PasswordIncorrectException;
import woowacourse.exception.notFound.CustomerNotFoundException;
import woowacourse.shoppingcart.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdateProfileRequest;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class CustomerServiceTest {

    @Autowired
    public CustomerService customerService;

    @DisplayName("회원가입을 할 수 있다.")
    @Test
    void create() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;

        //when
        final long id = customerService.create(회원생성요청);
        final CustomerResponse 회원조회응답 = customerService.findById(id);

        //then
        assertAll(
                () -> assertThat(회원조회응답.getEmail()).isEqualTo(회원생성요청.getEmail()),
                () -> assertThat(회원조회응답.getName()).isEqualTo(회원생성요청.getName())
        );
    }

    @DisplayName("이메일이 이미 존재하는 경우, 회원가입을 할 수 없다.")
    @Test
    void createWithExistedEmailShouldFail() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        customerService.create(회원생성요청);
        final CustomerRequest 다시회원생성요청 = new CustomerRequest("리차드", 잉_회원생성요청.getEmail(), "richard1234");

        //when then
        assertThatThrownBy(() -> customerService.create(다시회원생성요청))
                .isInstanceOf(EmailDuplicateException.class);
    }

    @DisplayName("회원 정보를 조회할 수 있다.")
    @Test
    void find() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        final long id = customerService.create(회원생성요청);

        // when
        final CustomerResponse 회원조회응답 = customerService.findById(id);

        // then
        assertAll(
                () -> assertThat(회원조회응답.getEmail()).isEqualTo(회원생성요청.getEmail()),
                () -> assertThat(회원조회응답.getName()).isEqualTo(회원생성요청.getName())
        );
    }

    @DisplayName("회원이름을 수정할 수 있다.")
    @Test
    void updateProfile() {
        // given
        final long id = customerService.create(잉_회원생성요청);
        final CustomerUpdateProfileRequest 잉_이름변경요청 = new CustomerUpdateProfileRequest(잉_회원생성요청.getName() + "수정");

        // when
        customerService.updateProfile(id, 잉_이름변경요청);
        final CustomerResponse 수정된_잉 = customerService.findById(id);

        // then
        assertThat(수정된_잉.getName()).isEqualTo(잉_이름변경요청.getName());
    }

    @DisplayName("존재하지 않는 회원인 경우, 회원 이름을 수정할 수 없다.")
    @Test
    void updateProfileWithInvalidIdShouldFail() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerUpdateProfileRequest 잉_이름변경요청 = new CustomerUpdateProfileRequest(잉_회원생성요청.getName() + "수정");

        // when then
        assertThatThrownBy(() -> customerService.updateProfile(100L, 잉_이름변경요청))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("비밀번호가 일치하지 않을 경우, 회원 비밀번호를 수정할 수 없다.")
    @Test
    void updatePasswordWithIncorrectPasswordShouldFail() {
        // given
        final long id = customerService.create(잉_회원생성요청);
        final CustomerUpdatePasswordRequest 잉_비밀번호수정요청 = new CustomerUpdatePasswordRequest(
                잉_회원생성요청.getPassword() + "incorrect", 잉_회원생성요청.getPassword() + "new");

        // when then
        assertThatThrownBy(() -> customerService.updatePassword(id, 잉_비밀번호수정요청))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("회원 비밀번호를 수정할 수 있다.")
    @Test
    void updatePassword() {
        // given
        final long id = customerService.create(잉_회원생성요청);
        final CustomerUpdatePasswordRequest 잉_비밀번호수정요청 = new CustomerUpdatePasswordRequest(
                잉_회원생성요청.getPassword(), 잉_회원생성요청.getPassword() + "new");

        // when then
        customerService.updatePassword(id, 잉_비밀번호수정요청);
        assertThatThrownBy(() -> customerService.updatePassword(id, 잉_비밀번호수정요청))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("비밀번호가 일치한다면 회원 탈퇴를 할 수 있다.")
    @Test
    void delete() {
        // given
        final long id = customerService.create(잉_회원생성요청);
        final CustomerDeleteRequest 잉회원탈퇴요청 = 잉_회원탈퇴요청;

        // when then
        customerService.delete(id, 잉회원탈퇴요청);
        assertThatThrownBy(() -> customerService.findById(id))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("비밀번호가 일치하지 않는다면 회원 탈퇴를 할 수 없다.")
    @Test
    void deleteWithIncorrectPasswordShouldFail() {
        // given
        final long id = customerService.create(잉_회원생성요청);
        final CustomerDeleteRequest 잉회원탈퇴요청 = new CustomerDeleteRequest(잉_회원생성요청.getPassword() + "stranger");

        // when then
        assertThatThrownBy(() -> customerService.delete(id, 잉회원탈퇴요청))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("존재하지 않는 회원인 경우, 회원 탈퇴를 할 수 없다.")
    @Test
    void deleteWithInvalidIdShouldFail() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerDeleteRequest 잉회원탈퇴요청 = new CustomerDeleteRequest(잉_회원생성요청.getPassword());
        final long id = 100L;

        // when then
        assertThatThrownBy(() -> customerService.delete(id, 잉회원탈퇴요청))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
