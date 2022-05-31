package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;
import static woowacourse.ShoppingCartFixture.잉_회원탈퇴요청;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.auth.PasswordIncorrectException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateRequest;

@JdbcTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class CustomerServiceTest {

    public final CustomerService customerService;

    public CustomerServiceTest(JdbcTemplate jdbcTemplate) {
        this.customerService = new CustomerService(new CustomerDao(jdbcTemplate), new BCryptPasswordEncoder());
    }

    @DisplayName("비밀번호가 일치한다면 회원이름을 수정할 수 있다.")
    @Test
    void update() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerUpdateRequest 잉_이름변경요청 = new CustomerUpdateRequest(잉_회원생성요청.getName() + "수정",
                잉_회원생성요청.getPassword());

        // when
        customerService.update(1L, 잉_이름변경요청);
        final CustomerResponse 수정된_잉 = customerService.findById(1L);

        // then
        assertThat(수정된_잉.getName()).isEqualTo(잉_이름변경요청.getName());
    }

    @DisplayName("비밀번호가 일치하지 않을 경우, 회원이름을 수정할 수 없다.")
    @Test
    void updateWithIncorrectPasswordShouldFail() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerUpdateRequest 잉_이름변경요청 = new CustomerUpdateRequest(잉_회원생성요청.getName() + "수정",
                잉_회원생성요청.getPassword() + "stranger");

        // when then
        assertThatThrownBy(() -> customerService.update(1L, 잉_이름변경요청))
                .isInstanceOf(PasswordIncorrectException.class);
    }

    @DisplayName("존재하지 않는 회원인 경우, 회원 이름을 수정할 수 없다.")
    @Test
    void updateWithInvalidIdShouldFail() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerUpdateRequest 잉_이름변경요청 = new CustomerUpdateRequest(잉_회원생성요청.getName() + "수정",
                잉_회원생성요청.getPassword());

        // when then
        assertThatThrownBy(() -> customerService.update(100L, 잉_이름변경요청))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("비밀번호가 일치한다면 회원 탈퇴를 할 수 있다.")
    @Test
    void delete() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerDeleteRequest 잉회원탈퇴요청 = 잉_회원탈퇴요청;
        final long id = 1L;

        // when then
        customerService.delete(id, 잉회원탈퇴요청);
        assertThatThrownBy(() -> customerService.findById(id))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("비밀번호가 일치하지 않는다면 회원 탈퇴를 할 수 없다.")
    @Test
    void deleteWithIncorrectPasswordShouldFail() {
        // given
        customerService.create(잉_회원생성요청);
        final CustomerDeleteRequest 잉회원탈퇴요청 = new CustomerDeleteRequest(잉_회원생성요청.getPassword() + "stranger");
        final long id = 1L;

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
