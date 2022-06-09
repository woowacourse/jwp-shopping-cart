package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;
import static woowacourse.fixture.PasswordFixture.encryptedReversePassword;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainReversePassword;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.DuplicateResponse;
import woowacourse.shoppingcart.exception.CannotUpdateUserNameException;
import woowacourse.shoppingcart.exception.DuplicatedNameException;
import woowacourse.shoppingcart.exception.IllegalFormException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @DisplayName("회원가입을 하고 id값을 반환한다.")
    @Test
    void signUp() {
        // given
        final String userName = "giron";
        given(customerDao.existsByUserName(userName))
                .willReturn(false);
        given(passwordEncryptor.encode(plainBasicPassword))
                .willReturn(encryptedBasicPassword.getValue());
        given(customerDao.save(userName, encryptedBasicPassword.getValue()))
                .willReturn(1L);

        // when
        final CustomerRequest request = new CustomerRequest(userName, plainBasicPassword);
        final Long id = customerService.signUp(request);

        // then
        assertAll(
                () -> assertThat(id).isEqualTo(1L),
                () -> verify(customerDao).existsByUserName(userName),
                () -> verify(passwordEncryptor).encode(plainBasicPassword),
                () -> verify(customerDao).save(userName, encryptedBasicPassword.getValue())
        );
    }

    @DisplayName("이미 있는 이름으로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpWithDuplicateUserName() {
        // given
        final String userName = "giron";
        given(customerDao.existsByUserName(userName)).willReturn(true);

        // when
        final CustomerRequest request = new CustomerRequest(userName, plainBasicPassword);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.signUp(request))
                        .isExactlyInstanceOf(DuplicatedNameException.class)
                        .hasMessageContaining("이미 가입된 이름이 있습니다."),
                () -> verify(customerDao).existsByUserName(userName)
        );
    }

    @DisplayName("잘못된 형식의 이름으로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpWithWrongUserName() {
        // given
        final String userName = "기론";
        given(customerDao.existsByUserName(userName)).willReturn(false);

        // when
        final CustomerRequest request = new CustomerRequest(userName, plainBasicPassword);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.signUp(request))
                        .isExactlyInstanceOf(IllegalFormException.class)
                        .hasMessageContaining("이름의 입력 형식에 맞지 않습니다."),
                () -> verify(customerDao).existsByUserName(userName)
        );
    }

    @DisplayName("잘못된 형식의 비밀번호로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpWithWrongPassword() {
        // given
        final String userName = "giron";
        given(customerDao.existsByUserName(userName)).willReturn(false);

        // when
        final CustomerRequest request = new CustomerRequest(userName, "123");

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.signUp(request))
                        .isExactlyInstanceOf(IllegalFormException.class)
                        .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다."),
                () -> verify(customerDao).existsByUserName(userName)
        );
    }

    @DisplayName("id를 통해서 회원 정보를 가져온다.")
    @Test
    void getMeById() {
        // given
        final Long id = 1L;
        final String userName = "giron";
        Customer customer = new Customer(id, new UserName(userName), encryptedBasicPassword);
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
        final String userName = "giron";
        Customer customer = new Customer(id, new UserName(userName), encryptedBasicPassword);
        Customer updatedCustomer = new Customer(id, new UserName(userName), encryptedReversePassword);
        given(customerDao.findById(id)).willReturn(Optional.of(customer));
        given(customerDao.update(id, userName, plainReversePassword)).willReturn(updatedCustomer);

        // when
        CustomerRequest request = new CustomerRequest("giron", plainReversePassword);
        CustomerResponse response = customerService.updateById(id, request);

        // then
        assertAll(
                () -> assertThat(response.getUserName()).isEqualTo("giron"),
                () -> verify(customerDao).findById(id),
                () -> verify(customerDao).update(id, userName, plainReversePassword)
        );
    }

    @DisplayName("존재하지 않는 유저의 id과 정보를 받아서 유저를 수정하면, 예외가 발생한다.")
    @Test
    void updateByWrongId() {
        // given
        final Long id = 0L;
        given(customerDao.findById(id)).willReturn(Optional.empty());

        // when
        CustomerRequest request = new CustomerRequest("giron", plainReversePassword);

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
        final String userName = "giron";
        Customer customer = new Customer(id, new UserName(userName), encryptedBasicPassword);
        given(customerDao.findById(id)).willReturn(Optional.of(customer));

        // when
        CustomerRequest request = new CustomerRequest("티키", plainBasicPassword);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> customerService.updateById(id, request))
                        .isExactlyInstanceOf(CannotUpdateUserNameException.class)
                        .hasMessageContaining("유저 이름을 변경할 수 없습니다."),
                () -> verify(customerDao).findById(id)
        );
    }

    @DisplayName("유저의 id를 통해서 유저를 삭제한다.")
    @Test
    void deleteById() {
        // given
        final Long id = 1L;
        final String userName = "giron";
        Customer customer = new Customer(id, new UserName(userName), encryptedBasicPassword);
        given(customerDao.findById(id)).willReturn(Optional.of(customer));
        doNothing().when(customerDao).deleteById(id);

        // then
        assertAll(
                () -> assertDoesNotThrow(() -> customerService.deleteById(id)),
                () -> verify(customerDao).findById(id),
                () -> verify(customerDao).deleteById(id)
        );
    }

    @DisplayName("유저의 이름이 주어졌을 때 중복이 된 이름인지 판단한다.")
    @Test
    void isDuplicateUserName() {
        // given
        final String userName = "giron";
        given(customerDao.existsByUserName(userName))
                .willReturn(true);

        // when
        final DuplicateResponse isDuplicateUserName = customerService.isDuplicateUserName(userName);

        // then
        assertAll(
                () -> assertThat(isDuplicateUserName.getIsDuplicate()).isTrue(),
                () -> verify(customerDao).existsByUserName(userName)
        );
    }
}
