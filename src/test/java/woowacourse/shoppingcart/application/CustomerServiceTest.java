package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.CannotUpdateUserNameException;
import woowacourse.shoppingcart.exception.DuplicatedNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

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
                        .isExactlyInstanceOf(DuplicatedNameException.class)
                        .hasMessageContaining("이미 가입된 이름이 있습니다."),
                () -> verify(customerDao).existsByUserName(userName)
        );
    }

    @DisplayName("id를 통해서 회원 정보를 가져온다.")
    @Test
    void getMeById() {
        // given
        final Long id = 1L;
        final String userName = "기론";
        final String password = "1234";
        Customer customer = new Customer(id, userName, password);
        given(customerDao.findById(id)).willReturn(Optional.of(customer));

        // when
        CustomerResponse response = customerService.getMeById(id);

        // then
        assertAll(
                () -> assertThat(response.getUserName()).isEqualTo(userName),
                () -> verify(customerDao).findById(id)
        );
    }

    @DisplayName("존재하지 않는 id를 통해서 회원 정보를 가져오면, 예외가 발생한다.")
    @Test
    void getMeByIdWithNotExistId() {
        // given
        final Long id = 1L;
        given(customerDao.findById(id)).willReturn(Optional.empty());

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.getMeById(id))
                        .isExactlyInstanceOf(InvalidCustomerException.class)
                        .hasMessageContaining("존재하지 않는 유저입니다."),
                () -> verify(customerDao).findById(id)
        );
    }

    @DisplayName("유저의 id과 정보를 받아서, 유저를 수정한다.")
    @Test
    void updateById() {
        // given
        final Long id = 1L;
        final String userName = "기론";
        final String password = "1234";
        Customer customer = new Customer(id, userName, password);
        Customer updatedCustomer = new Customer(id, userName, "321");
        given(customerDao.findById(id)).willReturn(Optional.of(customer));
        given(customerDao.update(id, userName, "321")).willReturn(updatedCustomer);

        // when
        CustomerRequest request = new CustomerRequest("기론", "321");
        CustomerResponse response = customerService.updateById(id, request);

        // then
        assertAll(
                () -> assertThat(response.getUserName()).isEqualTo("기론"),
                () -> verify(customerDao).findById(id),
                () -> verify(customerDao).update(id, userName, "321")
        );
    }

    @DisplayName("존재하지 않는 유저의 id과 정보를 받아서 유저를 수정하면, 예외가 발생한다.")
    @Test
    void updateByWrongId() {
        // given
        final Long id = 0L;
        given(customerDao.findById(id)).willReturn(Optional.empty());

        // when
        CustomerRequest request = new CustomerRequest("기론", "321");

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.updateById(id, request))
                        .isExactlyInstanceOf(InvalidCustomerException.class)
                        .hasMessageContaining("존재하지 않는 유저입니다."),
                () -> verify(customerDao).findById(id)
        );
    }

    @DisplayName("유저의 이름을 바꾸면은 예외가 발생한다.")
    @Test
    void updateUserName() {
        // given
        final Long id = 1L;
        final String userName = "기론";
        final String password = "1234";
        Customer customer = new Customer(id, userName, password);
        given(customerDao.findById(id)).willReturn(Optional.of(customer));

        // when
        CustomerRequest request = new CustomerRequest("티키", "321");

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.updateById(id, request))
                        .isExactlyInstanceOf(CannotUpdateUserNameException.class)
                        .hasMessageContaining("유저 이름을 변경할 수 없습니다."),
                () -> verify(customerDao).findById(id)
        );
    }
}
