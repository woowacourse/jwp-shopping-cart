package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static woowacourse.TestFixture.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.exception.bodyexception.DuplicateEmailException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @Test
    @DisplayName("이미 가입 이메일로 회원가입을 하면 예외를 던진다.")
    void create_alreadyExistEmail_exceptionThrown() {
        // given
        String email = "kun@naver.com";
        CustomerCreationRequest request = new CustomerCreationRequest(email, "1q2w3e4r", "kun");

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
        String email = "kun@naver.com";
        String nickname = "kun";
        String password = "qwerasdf123";
        CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        given(customerDao.existEmail(email))
                .willReturn(false);

        long expected = 1L;
        Customer customer = new Customer(nickname, email, password);
        given(customerDao.save(customer))
                .willReturn(expected);

        // when
        Long actual = customerService.create(request);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 예외를 발생시킨다.")
    void getByEmail_notExistEmail_exceptionThrown() {
        // given
        String email = "asdf@email.com";

        given(customerDao.findByEmail(email))
                .willThrow(new IllegalArgumentException("이메일이 존재하지 않습니다."));

        // when, then
        assertThatThrownBy(() -> customerService.getByEmail(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이메일이 존재하는 경우에, Customer를 반환한다.")
    void getByEmail_existEmail_customerReturned() {
        // given
        String email = "email@email.com";
        Customer expected = new Customer("kun", email, "qwerasdf123");
        given(customerDao.findByEmail(email))
                .willReturn(expected);

        // when
        Customer actual = customerService.getByEmail(email);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Customer를 수정한다.")
    void update_customer_void() {
        // given
        String email = "kun@email.com";
        String password = "qwerasdf321";

        CustomerUpdationRequest request = new CustomerUpdationRequest("rick", "qwerasdf123");

        // when, then
        assertThatCode(() -> customerService.update(customer, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Customer를 삭제한다.")
    void delete_customer_void() {
        // given
        given(customerDao.findByEmail(customer.getEmail()))
                .willThrow(new IllegalArgumentException("이메일이 존재하지 않습니다."));

        // when, then
        assertAll(
                () -> assertThatCode(() -> customerService.delete(customer))
                        .doesNotThrowAnyException(),
                () -> assertThatThrownBy(() -> customerService.getByEmail(customer.getEmail()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이메일이 존재하지 않습니다.")
        );
    }
}
