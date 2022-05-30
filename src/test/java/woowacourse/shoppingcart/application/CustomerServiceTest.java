package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        final CustomerDto customerDto = customerService.createCustomer(signupRequest);

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
        assertThatThrownBy(() -> customerService.createCustomer(signupRequest))
                .isInstanceOf(DuplicatedAccountException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }
}
