package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final String NAME = "라라";
    private static final String EMAIL = "lala.seeun@gmail.com";
    private static final String PASSWORD = "tpdmstpdms11";
    private static final PlainPassword PLAIN_PASSWORD = new PlainPassword(PASSWORD);
    private static final EncodedPassword ENCODED_PASSWORD = new EncodedPassword(PLAIN_PASSWORD.encode());

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @Test
    @DisplayName("회원을 등록한다.")
    void save() {
        // given
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        final Customer customer = new Customer(1L, request.getName(),
                new Email(request.getEmail()),
                ENCODED_PASSWORD);

        when(customerDao.save(any(Customer.class)))
                .thenReturn(customer);

        // when, then
        assertThatCode(() -> customerService.save(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("중복된 이메일로 회원 등록 시 예외가 발생한다.")
    void save_duplicatedEmail_throwsException() {
        // given
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        when(customerDao.existsByEmail(any(Customer.class)))
                .thenReturn(true);

        // when, then
        assertThatThrownBy(() -> customerService.save(request))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("id로 회원 정보를 조회한다.")
    void findById() {
        // given
        final long id = 1L;
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        final Customer customer = new Customer(id, request.getName(),
                new Email(request.getEmail()),
                ENCODED_PASSWORD);
        when(customerDao.findById(any(Long.class)))
                .thenReturn(Optional.of(customer));

        // when
        CustomerDetailResponse actual = customerService.findById(id);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new CustomerDetailResponse(NAME, EMAIL));
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    void delete() {
        // given
        final CustomerDeleteRequest request = new CustomerDeleteRequest(PASSWORD);
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), ENCODED_PASSWORD)));
        when(customerDao.deleteById(1L))
                .thenReturn(1);

        // when
        assertThatCode(() -> customerService.delete(1L, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 삭제시 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    void delete_passwordNotMatch_throwsException() {
        // given
        final CustomerDeleteRequest request
                = new CustomerDeleteRequest("1111111111");
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), ENCODED_PASSWORD)));

        // when, then
        assertThatThrownBy(() -> customerService.delete(1L, request))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    @DisplayName("회원의 이름을 수정한다.")
    void updateName() {
        // given
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), ENCODED_PASSWORD)));
        when(customerDao.updateById(any(Customer.class)))
                .thenReturn(1);

        // when, then
        final CustomerProfileUpdateRequest request = new CustomerProfileUpdateRequest("클레이");
        assertThatCode(() -> customerService.updateName(1L, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원의 비밀번호를 수정한다.")
    void updatePassword() {
        // given
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), ENCODED_PASSWORD)));
        when(customerDao.updateById(any(Customer.class)))
                .thenReturn(1);

        // when, then
        final CustomerPasswordUpdateRequest request = new CustomerPasswordUpdateRequest(PASSWORD, "newpassword123");
        assertThatCode(() -> customerService.updatePassword(1L, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 비밀번호 수정시 기존 비밀번호와 다를 경우 예외가 발생한다.")
    void updatePassword_passwordNotMatch_throwsException() {
        // given
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, NAME, new Email(EMAIL), ENCODED_PASSWORD)));

        // when
        final CustomerPasswordUpdateRequest request =
                new CustomerPasswordUpdateRequest("wrongoldpassword", "newpassword123");

        // then
        assertThatThrownBy(() -> customerService.updatePassword(1L, request))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}
