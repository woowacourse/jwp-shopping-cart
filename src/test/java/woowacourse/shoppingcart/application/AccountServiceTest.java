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
import woowacourse.shoppingcart.dao.AccountDao;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.AccountUpdateRequest;
import woowacourse.shoppingcart.exception.DuplicateAccountException;
import woowacourse.shoppingcart.exception.InvalidAccountException;

@SpringBootTest
@Transactional
class AccountServiceTest {

    private static final String EMAIL = "tonic@email.com";
    private static final String PASSWORD = "12345678a";
    private static final String ENCRYPT_PASSWORD = PasswordEncoder.encrypt(PASSWORD);
    private static final String NICKNAME = "토닉";
    private static final String NOT_FOUND_EMAIL = "notFoundEmail@email.com";

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDao accountDao;

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NICKNAME);
        accountService.saveAccount(request);
    }

    @DisplayName("정상적으로 회원 등록")
    @Test
    void addCustomer() {
        Account account = accountService.findByEmail(EMAIL);
        assertAll(
                () -> assertThat(account.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(account.getPassword()).isEqualTo(ENCRYPT_PASSWORD),
                () -> assertThat(account.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(account.getId()).isNotNull()
        );
    }

    @DisplayName("중복된 email로 회원 등록")
    @Test
    void duplicatedEmailCustomer() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NICKNAME);
        assertThatThrownBy(() -> accountService.saveAccount(request))
                .isInstanceOf(DuplicateAccountException.class);
    }

    @DisplayName("email로 회원 조회")
    @Test
    void findByEmail() {
        Account account = accountService.findByEmail(EMAIL);

        assertAll(
                () -> assertThat(account.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(account.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(account.getPassword()).isEqualTo(ENCRYPT_PASSWORD));
    }

    @DisplayName("가입하지 않은 email로 회원 조회 시 예외 발생")
    @Test
    void notFoundCustomerByEmailThrowException() {
        assertThatThrownBy(() -> accountService.findByEmail(NOT_FOUND_EMAIL))
                .isInstanceOf(InvalidAccountException.class);
    }

    @DisplayName("존재하지 않는 이메일로 탈퇴 시 예외 발생")
    @Test
    void deleteByNotExistEmail() {
        assertThatThrownBy(() -> accountService.deleteByEmail(NOT_FOUND_EMAIL))
                .isInstanceOf(InvalidAccountException.class);
    }

    @DisplayName("이메일로 회원 탈퇴")
    @Test
    void deleteByEmail() {
        accountService.deleteByEmail(EMAIL);

        assertThat(accountDao.existByEmail(EMAIL)).isFalse();
    }

    @DisplayName("존재하지 않는 이메일로 수정 시 예외 발생")
    @Test
    void updateByNotExistEmail() {
        assertThatThrownBy(() -> accountService.updateCustomer(NOT_FOUND_EMAIL,
                new AccountUpdateRequest(NICKNAME, PASSWORD)))
                .isInstanceOf(InvalidAccountException.class);
    }

    @DisplayName("정상적인 회원 정보 수정")
    @Test
    void updateCustomer() {
        String newNickname = "토닉2";
        String newPassword = "newPassword1";
        accountService.updateCustomer(EMAIL, new AccountUpdateRequest(newNickname, newPassword));
        Account account = accountService.findByEmail(EMAIL);

        assertThat(account.getPassword()).isEqualTo(PasswordEncoder.encrypt(newPassword));
        assertThat(account.getNickname()).isEqualTo(newNickname);
    }
}
