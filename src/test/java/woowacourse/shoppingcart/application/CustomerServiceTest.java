package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerServiceTest {

    @Autowired
    private CustomerDao customerDao;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerDao);
    }

    @DisplayName("아이디에 null 을 입력하면 안된다.")
    @Test
    void signUpUserIdNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(null, "유콩", "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("아이디에 빈값을 입력하면 안된다.")
    void signUpUserIdBlankException(String userId) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(userId, "유콩", "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("닉네임에 null 을 입력하면 안된다.")
    @Test
    void signUpNicknamedNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", null, "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임에 빈값을 입력하면 안된다.")
    void signUpNicknameBlankException(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", nickname, "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void signUpPasswordNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", "유콩", null);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 안된다.")
    void signUpPasswordBlankException(String password) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", "유콩", password);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @DisplayName("중복된 아이디로 가입할 수 없다.")
    @Test
    void validateDuplicateUserId() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("puterism@woowacourse.com", "유콩", "1234");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @DisplayName("중복된 닉네임을 가입할 수 없다.")
    @Test
    void validateDuplicateNickname() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", "nickname1", "1234");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("아이디는 이메일 형식이 아니면 안된다.")
    @Test
    void validateUserIdFormat() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim", "nickname1", "1234");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디는 이메일 형식으로 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaa", "!@#$"})
    @DisplayName("닉네임이 영문, 한글, 숫자를 조합하여 2 ~ 10 자가 아니면 안된다.")
    void validateNicknameFormat(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", nickname, "1234");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.");
    }
}
