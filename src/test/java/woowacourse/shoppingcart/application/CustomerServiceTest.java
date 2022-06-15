package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.shoppingcart.application.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerDetailServiceResponse;
import woowacourse.shoppingcart.application.dto.CustomerPasswordUpdateServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerProfileUpdateServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final String NAME = "라라";
    private static final String EMAIL = "lala.seeun@gmail.com";
    private static final String PASSWORD = "tpdmstpdms11";

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @Test
    @DisplayName("회원을 등록한다.")
    void save() {
        // given
        final CustomerSaveServiceRequest customerSaveServiceRequest = new CustomerSaveServiceRequest(NAME, EMAIL,
                PASSWORD);
        final Customer customer = new Customer(1L, customerSaveServiceRequest.getName(),
                new Email(customerSaveServiceRequest.getEmail()),
                Password.fromRawValue(customerSaveServiceRequest.getPassword()));
        when(customerDao.save(any(Customer.class)))
                .thenReturn(customer);

        // when, then
        assertThatCode(() -> customerService.save(customerSaveServiceRequest))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("중복된 이메일로 회원 등록 시 예외가 발생한다.")
    void save_duplicatedEmail_throwsException() {
        // given
        final CustomerSaveServiceRequest customerSaveServiceRequest = new CustomerSaveServiceRequest(NAME, EMAIL,
                PASSWORD);
        when(customerDao.existsByEmail(any(Customer.class)))
                .thenReturn(true);

        // when, then
        assertThatThrownBy(() -> customerService.save(customerSaveServiceRequest))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("id로 회원 정보를 조회한다.")
    void findById() {
        // given
        final long id = 1L;
        final CustomerSaveServiceRequest customerSaveServiceRequest = new CustomerSaveServiceRequest(NAME, EMAIL,
                PASSWORD);
        final Customer customer = new Customer(id, customerSaveServiceRequest.getName(),
                new Email(customerSaveServiceRequest.getEmail()),
                Password.fromRawValue(customerSaveServiceRequest.getPassword()));
        when(customerDao.findById(any(Long.class)))
                .thenReturn(Optional.of(customer));

        // when
        final CustomerDetailServiceResponse actual = customerService.findById(id);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new CustomerDetailServiceResponse(NAME, EMAIL));
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    void delete() {
        // given
        final CustomerDeleteServiceRequest request
                = new CustomerDeleteServiceRequest(1L, PASSWORD);
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), Password.fromRawValue(PASSWORD))));
        when(customerDao.deleteById(1L))
                .thenReturn(1);

        // when
        assertThatCode(() -> customerService.delete(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 삭제시 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    void delete_passwordNotMatch_throwsException() {
        // given
        final CustomerDeleteServiceRequest request
                = new CustomerDeleteServiceRequest(1L, "1111111111");
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), Password.fromRawValue(PASSWORD))));

        // when, then
        assertThatThrownBy(() -> customerService.delete(request))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    @DisplayName("회원의 이름을 수정한다.")
    void updateName() {
        // given
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), Password.fromRawValue(PASSWORD))));
        when(customerDao.updateById(any(Customer.class)))
                .thenReturn(1);

        // when, then
        final CustomerProfileUpdateServiceRequest request = new CustomerProfileUpdateServiceRequest(1L, "클레이");
        assertThatCode(() -> customerService.updateName(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원의 비밀번호를 수정한다.")
    void updatePassword() {
        // given
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), Password.fromRawValue(PASSWORD))));
        when(customerDao.updateById(any(Customer.class)))
                .thenReturn(1);

        // when, then
        final CustomerPasswordUpdateServiceRequest request = new CustomerPasswordUpdateServiceRequest(1L, PASSWORD,
                "newpassword123");
        assertThatCode(() -> customerService.updatePassword(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 비밀번호 수정시 기존 비밀번호와 다를 경우 예외가 발생한다.")
    void updatePassword_passwordNotMatch_throwsException() {
        // given
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), Password.fromRawValue(PASSWORD))));

        // when
        final CustomerPasswordUpdateServiceRequest request = new CustomerPasswordUpdateServiceRequest(1L,
                "wrongoldpassword", "newpassword123");

        // then
        assertThatThrownBy(() -> customerService.updatePassword(request))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}
