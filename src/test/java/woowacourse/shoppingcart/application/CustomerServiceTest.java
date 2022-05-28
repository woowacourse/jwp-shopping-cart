package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;

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
}
