package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;
import static woowacourse.ShoppingCartFixture.잉_회원탈퇴요청;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.notfound.CustomerNotFoundException;
import woowacourse.exception.unauthorized.PasswordIncorrectException;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.infra.JdbcCustomerRepository;
import woowacourse.shoppingcart.infra.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateProfileRequest;

@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CustomerServiceTest {
    public final SpringCustomerService customerService;

    public CustomerServiceTest(JdbcTemplate jdbcTemplate) {
        this.customerService = new SpringCustomerService(
                new JdbcCustomerRepository(new JdbcCustomerDao(jdbcTemplate)),
                new BCryptPasswordEncoder());
    }

    @DisplayName("회원이름을 수정할 수 있다.")
    @Test
    void updateProfile() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerUpdateProfileRequest 잉_이름변경요청 = new CustomerUpdateProfileRequest(잉_회원생성요청.getName() + "수정");

        // when
        customerService.updateProfile(1L, 잉_이름변경요청.toServiceRequest());
        final Customer 수정된_잉 = customerService.getById(1L);

        // then
        assertThat(수정된_잉.getName()).isEqualTo(잉_이름변경요청.getName());
    }

    @DisplayName("존재하지 않는 회원인 경우, 회원 이름을 수정할 수 없다.")
    @Test
    void updateProfileWithInvalidIdShouldFail() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerUpdateProfileRequest 잉_이름변경요청 = new CustomerUpdateProfileRequest(잉_회원생성요청.getName() + "수정");

        // when then
        assertThatThrownBy(() -> customerService.updateProfile(100L, 잉_이름변경요청.toServiceRequest()))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("비밀번호가 일치하지 않을 경우, 회원 비밀번호를 수정할 수 없다.")
    @Test
    void updatePasswordWithIncorrectPasswordShouldFail() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerUpdatePasswordRequest 잉_비밀번호수정요청 = new CustomerUpdatePasswordRequest(
                잉_회원생성요청.getPassword() + "incorrect", 잉_회원생성요청.getPassword() + "new");

        // when then
        assertThatThrownBy(() -> customerService.updatePassword(1L, 잉_비밀번호수정요청.toServiceRequest()))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("회원 비밀번호를 수정할 수 있다.")
    @Test
    void updatePassword() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerUpdatePasswordRequest 잉_비밀번호수정요청 = new CustomerUpdatePasswordRequest(
                잉_회원생성요청.getPassword(), 잉_회원생성요청.getPassword() + "new");

        // when then
        customerService.updatePassword(1L, 잉_비밀번호수정요청.toServiceRequest());
        assertThatThrownBy(() -> customerService.updatePassword(1L, 잉_비밀번호수정요청.toServiceRequest()))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("비밀번호가 일치한다면 회원 탈퇴를 할 수 있다.")
    @Test
    void delete() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerDeleteRequest 잉회원탈퇴요청 = 잉_회원탈퇴요청;
        final long id = 1L;

        // when then
        customerService.delete(id, 잉회원탈퇴요청.toServiceRequest());
        assertThatThrownBy(() -> customerService.getById(id))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("비밀번호가 일치하지 않는다면 회원 탈퇴를 할 수 없다.")
    @Test
    void deleteWithIncorrectPasswordShouldFail() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerDeleteRequest 잉회원탈퇴요청 = new CustomerDeleteRequest(잉_회원생성요청.getPassword() + "stranger");
        final long id = 1L;

        // when then
        assertThatThrownBy(() -> customerService.delete(id, 잉회원탈퇴요청.toServiceRequest()))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("존재하지 않는 회원인 경우, 회원 탈퇴를 할 수 없다.")
    @Test
    void deleteWithInvalidIdShouldFail() {
        // given
        customerService.create(잉_회원생성요청.toServiceRequest());
        final CustomerDeleteRequest 잉회원탈퇴요청 = new CustomerDeleteRequest(잉_회원생성요청.getPassword());
        final long id = 100L;

        // when then
        assertThatThrownBy(() -> customerService.delete(id, 잉회원탈퇴요청.toServiceRequest()))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
