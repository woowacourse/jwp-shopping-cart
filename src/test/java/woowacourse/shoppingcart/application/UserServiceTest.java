package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.Encoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UserUpdateRequest;
import woowacourse.shoppingcart.exception.badrequest.DuplicateUserException;
import woowacourse.shoppingcart.exception.badrequest.InvalidUserException;

@SpringBootTest
@Transactional
class UserServiceTest {

    private static final String EMAIL = "tonic@email.com";
    private static final String PASSWORD = "12345678a";
    private static final String NICKNAME = "토닉";
    private static final String NOT_FOUND_EMAIL = "notFoundEmail@email.com";

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Encoder encoder;

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NICKNAME);
        userService.registerCustomer(request);
    }

    @DisplayName("정상적으로 회원 등록")
    @Test
    void addCustomer() {
        Customer customer = userService.findByEmail(EMAIL);
        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getPassword()).isEqualTo(encoder.encrypt(PASSWORD)),
                () -> assertThat(customer.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(customer.getId()).isNotNull()
        );
    }

    @DisplayName("중복된 email로 회원 등록")
    @Test
    void duplicatedEmailCustomer() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NICKNAME);
        assertThatThrownBy(() -> userService.registerCustomer(request))
                .isInstanceOf(DuplicateUserException.class);
    }

    @DisplayName("email로 회원 조회")
    @Test
    void findByEmail() {
        Customer customer = userService.findByEmail(EMAIL);

        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customer.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(customer.getPassword()).isEqualTo(encoder.encrypt(PASSWORD)));
    }

    @DisplayName("가입하지 않은 email로 회원 조회 시 예외 발생")
    @Test
    void notFoundCustomerByEmailThrowException() {
        assertThatThrownBy(() -> userService.findByEmail(NOT_FOUND_EMAIL))
                .isInstanceOf(InvalidUserException.class);
    }

    @DisplayName("존재하지 않는 이메일로 탈퇴 시 예외 발생")
    @Test
    void deleteByNotExistEmail() {
        assertThatThrownBy(() -> userService.deleteByEmail(NOT_FOUND_EMAIL))
                .isInstanceOf(InvalidUserException.class);
    }

    @DisplayName("이메일로 회원 탈퇴")
    @Test
    void deleteByEmail() {
        userService.deleteByEmail(EMAIL);

        assertThat(customerDao.existByEmail(EMAIL)).isFalse();
    }

    @DisplayName("존재하지 않는 이메일로 수정 시 예외 발생")
    @Test
    void updateByNotExistEmail() {
        assertThatThrownBy(() -> userService.updateCustomer(NOT_FOUND_EMAIL,
                new UserUpdateRequest(NICKNAME, PASSWORD)))
                .isInstanceOf(InvalidUserException.class);
    }

    @DisplayName("정상적인 회원 정보 수정")
    @Test
    void updateCustomer() {
        String newNickname = "토닉2";
        String newPassword = "newPassword1";
        userService.updateCustomer(EMAIL, new UserUpdateRequest(newNickname, newPassword));
        Customer customer = userService.findByEmail(EMAIL);

        assertThat(customer.getPassword()).isEqualTo(encoder.encrypt(newPassword));
        assertThat(customer.getNickname()).isEqualTo(newNickname);
    }
}
