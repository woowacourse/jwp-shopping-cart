package cart.domain.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("ID, 이메일, 비밀번호, 이름, 휴대전화 번호를 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenRequest() {
        assertDoesNotThrow(() -> User.create(
                1L,
                "test@test.com",
                "1234abcd!@",
                "김철수",
                "01012341234"
        ));
    }

    @DisplayName("ID, 이메일, 비밀번호, 휴대전화 번호를 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutName() {
        assertDoesNotThrow(() -> User.create(
                1L,
                "test@test.com",
                "1234abcd!@",
                null,
                "01012341234"
        ));
    }

    @DisplayName("ID, 이메일, 비밀번호, 이름을 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutPhoneNumber() {
        assertDoesNotThrow(() -> User.create(
                1L,
                "test@test.com",
                "1234abcd!@",
                "김철수",
                null
        ));
    }

    @DisplayName("ID, 이메일, 비밀번호를 가진 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutNameAndPhoneNumber() {
        assertDoesNotThrow(() -> User.create(
                1L,
                "test@test.com",
                "1234abcd!@",
                null,
                null
        ));
    }

    @DisplayName("ID가 없는 User를 생성한다.")
    @Test
    void shouldSuccessToCreateUserWhenCreateWithoutId() {
        assertDoesNotThrow(() -> User.createToSave(
                "test@test.com",
                "1234abcd!@",
                "김철수",
                "01012341234"
        ));
    }
}
