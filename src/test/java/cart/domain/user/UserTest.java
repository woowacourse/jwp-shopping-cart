package cart.domain.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("이메일, 비밀번호, 이름, 휴대전화 번호를 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenRequest() {
        assertDoesNotThrow(() -> User.of(
                "test@test.com",
                "1234abcd!@",
                "김철수",
                "01012341234"
        ));
    }

    @DisplayName("이메일, 비밀번호, 휴대전화 번호를 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutName() {
        assertDoesNotThrow(() -> User.of(
                "test@test.com",
                "1234abcd!@",
                null,
                "01012341234"
        ));
    }

    @DisplayName("이메일, 비밀번호, 이름을 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutPhoneNumber() {
        assertDoesNotThrow(() -> User.of(
                "test@test.com",
                "1234abcd!@",
                "김철수",
                null
        ));
    }

    @DisplayName("이메일, 비밀번호를 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutNameAndPhoneNumber() {
        assertDoesNotThrow(() -> User.of(
                "test@test.com",
                "1234abcd!@",
                null,
                null
        ));
    }
}
