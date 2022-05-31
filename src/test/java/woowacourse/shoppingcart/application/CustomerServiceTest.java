package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.auth.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;

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
        this.customerService = new CustomerService(customerDao);
    }

    @Test
    @DisplayName("회원을 생성한다.")
    void createCustomer() {
        // given
        given(customerDao.findByAccount(any(String.class))).willReturn(Optional.empty());
        final Customer expected = new Customer(1L, "hamcheeseburger", "corinne", "password123", "코린네", "01012345678");
        given(customerDao.save(any(Customer.class))).willReturn(expected);

        // when
        final SignupRequest signupRequest = new SignupRequest("hamcheeseburger", "corinne", "password123", "코린네", new PhoneNumber("010", "1234", "5678"));
        final CustomerDto customerDto = customerService.create(signupRequest);

        // then
        assertThat(customerDto.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 존재하는 아이디로 회원을 생성하면 예외를 발생한다.")
    void thrownWhenExistAccount() {
        // given
        final Customer expected = new Customer(1L, "hamcheeseburger", "corinne", "password123", "코린네", "01012345678");
        given(customerDao.findByAccount(any(String.class))).willReturn(Optional.of(expected));

        // when
        final SignupRequest signupRequest = new SignupRequest("hamcheeseburger", "corinne", "password123", "코린네", new PhoneNumber("010", "1234", "5678"));

        // then
        assertThatThrownBy(() -> customerService.create(signupRequest))
                .isInstanceOf(DuplicatedAccountException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("사용자를 id로 조회한다.")
    void findById() {
        // given
        final Customer expected = new Customer(1L, "hamcheeseburger", "corinne", "password123", "코린네", "01012345678");
        given(customerDao.findById(any(Long.class))).willReturn(Optional.of(expected));

        // when
        final CustomerDto customerDto = customerService.getById(1L);

        // then
        assertAll(
                () -> assertThat(customerDto.getId()).isEqualTo(1L),
                () -> assertThat(customerDto.getAccount()).isEqualTo("hamcheeseburger"),
                () -> assertThat(customerDto.getNickname()).isEqualTo("corinne"),
                () -> assertThat(customerDto.getAddress()).isEqualTo("코린네"),
                () -> assertThat(customerDto.getPhoneNumber().appendNumbers()).isEqualTo("01012345678")
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
        final int affectedRows = customerService.update(1L, new UpdateCustomerRequest("hamcheeseburger", "코린네", new PhoneNumber("010", "1234", "1234")));

        // then
        assertThat(affectedRows).isEqualTo(1);
    }
}
