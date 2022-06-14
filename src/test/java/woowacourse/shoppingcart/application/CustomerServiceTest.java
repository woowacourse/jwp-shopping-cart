package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PhoneNumberFormat;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class CustomerServiceTest {

    private final CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    public CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.customerService = new CustomerService(customerDao, new FakePasswordEncoder());
    }

    @Test
    @DisplayName("회원을 생성한다.")
    void createCustomer() {
        // given
        given(customerDao.findByAccount(any(String.class))).willReturn(Optional.empty());
        final Customer expected = new Customer(
                1L,
                new Account("hamcheeseburger"),
                new Nickname("corinne"),
                new EncodedPassword("password123"),
                new Address("코린네"),
                new PhoneNumber("01012345678"));
        given(customerDao.save(any(Customer.class))).willReturn(expected);

        // when
        final SignupRequest signupRequest = new SignupRequest("hamcheeseburger", "corinne", "Password123!", "코린네", new PhoneNumberFormat("010", "1234", "5678"));
        final CustomerResponse customerResponse = customerService.create(signupRequest);

        // then
        assertThat(customerResponse.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 존재하는 아이디로 회원을 생성하면 예외를 발생한다.")
    void thrownWhenExistAccount() {
        // given
        final Customer expected = new Customer(
                1L,
                new Account("hamcheeseburger"),
                new Nickname("corinne"),
                new EncodedPassword("password123"),
                new Address("코린네"),
                new PhoneNumber("01012345678"));
        given(customerDao.findByAccount(any(String.class))).willReturn(Optional.of(expected));

        // when
        final SignupRequest signupRequest = new SignupRequest("hamcheeseburger", "corinne", "Password123!", "코린네", new PhoneNumberFormat("010", "1234", "5678"));

        // then
        assertThatThrownBy(() -> customerService.create(signupRequest))
                .isInstanceOf(DuplicatedAccountException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("사용자를 id로 조회한다.")
    void findById() {
        // given
        final Customer expected = new Customer(
                1L,
                new Account("hamcheeseburger"),
                new Nickname("corinne"),
                new EncodedPassword("password123"),
                new Address("코린네"),
                new PhoneNumber("01012345678"));
        given(customerDao.findById(any(Long.class))).willReturn(Optional.of(expected));

        // when
        final CustomerResponse customerResponse = customerService.getById(1L);

        // then
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getAccount()).isEqualTo("hamcheeseburger"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("corinne"),
                () -> assertThat(customerResponse.getAddress()).isEqualTo("코린네"),
                () -> assertThat(customerResponse.getPhoneNumber().appendNumbers()).isEqualTo("01012345678")
        );

    }

    @Test
    @DisplayName("존재하지 않는 id로 사용자를 조회하면 예외를 발생한다.")
    void throwNotExistId() {
        // given
        given(customerDao.findById(any(Long.class))).willReturn(Optional.empty());

        // when

        // then
        assertThatThrownBy(
                () -> customerService.getById(1L)
        )
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("회원을 찾을 수 없습니다.");
    }


    @Test
    @DisplayName("id와 변경할 회원 정보로 회원 정보를 수정한다.")
    void updateCustomer() {
        // given
        given(customerDao.update(any(Long.class),
                any(String.class),
                any(String.class),
                any(String.class))).willReturn(1);

        // when
        final int affectedRows = customerService.update(1L, new UpdateCustomerRequest("hamcheeseburger", "코린네", new PhoneNumberFormat("010", "1234", "1234")));

        // then
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void delete() {
        // given
        final Customer expected = new Customer(
                1L,
                new Account("hamcheeseburger"),
                new Nickname("corinne"),
                new EncodedPassword("Password123!"),
                new Address("코린네"),
                new PhoneNumber("01012345678"));
        given(customerDao.findById(1L)).willReturn(Optional.of(expected));
        given(customerDao.deleteById(1L)).willReturn(1);

        // when
        final int affectedRows = customerService.delete(1L, new DeleteCustomerRequest("Password123!"));
        // then
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    @DisplayName("회원을 탈퇴할 때 비밀번호가 일치하지 않으면 예외를 발생한다.")
    void throwWhenPasswordNotMatch() {
        // given
        final Customer expected = new Customer(
                1L,
                new Account("hamcheeseburger"),
                new Nickname("corinne"),
                new EncodedPassword("password123"),
                new Address("코린네"),
                new PhoneNumber("01012345678"));
        given(customerDao.findById(1L)).willReturn(Optional.of(expected));

        // when

        // then
        assertThatThrownBy(() -> customerService.delete(1L, new DeleteCustomerRequest("Paassword123!")))
                .isInstanceOf(WrongPasswordException.class)
                .hasMessage("비밀번호가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("회원을 탈퇴할 때 회원이 존재하지 않으면 예외를 발생한다.")
    void throwWhenCustomerNotExist() {
        // given
        given(customerDao.findById(1L)).willReturn(Optional.empty());
        // when

        // then
        assertThatThrownBy(() -> customerService.delete(1L, new DeleteCustomerRequest("Password123!")))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("회원을 찾을 수 없습니다.");
    }
}
