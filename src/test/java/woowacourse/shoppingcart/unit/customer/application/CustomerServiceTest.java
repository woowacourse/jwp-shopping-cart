package woowacourse.shoppingcart.unit.customer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.customer.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.customer.exception.badrequest.DuplicateEmailException;
import woowacourse.shoppingcart.customer.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.unit.ServiceMockTest;

class CustomerServiceTest extends ServiceMockTest {

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("이미 가입 이메일로 회원가입을 하면 예외를 던진다.")
    void create_alreadyExistEmail_exceptionThrown() {
        // given
        final String email = "kun@naver.com";
        final CustomerCreationRequest request = new CustomerCreationRequest(email, "1q2w3e4r", "kun");

        given(customerDao.existEmail(email))
                .willReturn(true);

        // when, then
        Assertions.assertThatThrownBy(() -> customerService.create(request))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void create() {
        // given
        final String email = "kun@naver.com";
        final String nickname = "kun";
        final String password = "qwerasdf123";
        final CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        given(customerDao.existEmail(email))
                .willReturn(false);

        final long expected = 1L;
        final Customer customer = new Customer(nickname, email, password);
        given(customerDao.save(customer))
                .willReturn(expected);

        // when
        final Long actual = customerService.create(request);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 예외를 발생시킨다.")
    void getByEmail_notExistEmail_exceptionThrown() {
        // given
        final String email = "asdf@email.com";

        given(customerDao.findByEmail(email))
                .willThrow(NotFoundCustomerException.class);

        // when, then
        assertThatThrownBy(() -> customerService.getByEmail(email))
                .isInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    @DisplayName("이메일이 존재하는 경우에, Customer를 반환한다.")
    void getByEmail_existEmail_customerReturned() {
        // given
        final String email = "email@email.com";
        final Customer expected = new Customer("kun", email, "qwerasdf123");
        given(customerDao.findByEmail(email))
                .willReturn(expected);

        // when
        final Customer actual = customerService.getByEmail(email);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Customer를 수정한다.")
    void update_customer_void() {
        // given
        final String email = "kun@email.com";
        final Customer customer = new Customer(1L, "kun", email, HASH);

        final CustomerUpdationRequest request = new CustomerUpdationRequest("rick", "qwerasdf123");

        // when, then
        assertThatCode(() -> customerService.update(customer, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Customer를 삭제한다.")
    void delete_customer_void() {
        // given
        final Customer customer = new Customer(1L, "kun", "kun@email.com", HASH);

        // when, then
        assertThatCode(() -> customerService.delete(customer))
                .doesNotThrowAnyException();
    }
}
