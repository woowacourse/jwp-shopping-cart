package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.DuplicatedNameException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @DisplayName("회원가입을 하고 id값을 반환한다.")
    @Test
    void signUp() {
        // given
        final String userName = "기론";
        final String password = "1234";
        given(customerDao.existsByUserName(userName)).willReturn(false);
        given(customerDao.save(userName, password)).willReturn(1L);

        // when
        final CustomerRequest request = new CustomerRequest(userName, password);
        final Long id = customerService.signUp(request);

        // then
        assertAll(
                () -> assertThat(id).isEqualTo(1L),
                () -> verify(customerDao).existsByUserName(userName),
                () -> verify(customerDao).save(userName, password)
        );
    }

    @DisplayName("이미 있는 이름으로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpWithDuplicateUserName() {
        // given
        final String userName = "기론";
        final String password = "1234";
        given(customerDao.existsByUserName(userName)).willReturn(true);

        // when
        final CustomerRequest request = new CustomerRequest(userName, password);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.signUp(request))
                        .isInstanceOf(DuplicatedNameException.class)
                        .hasMessageContaining("이미 가입된 이름이 있습니다."),
                () -> verify(customerDao).existsByUserName(userName)
        );
    }
}
