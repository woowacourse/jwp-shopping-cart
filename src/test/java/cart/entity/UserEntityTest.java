package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserEntityTest {

    @Test
    @DisplayName("생성 성공 케이스")
    void create_success() {
        assertAll(
                () -> assertThatCode(() -> new UserEntity("hello@hello.com", "12345678"))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new UserEntity("hello@hello.com", "12345678", "hello"))
                        .doesNotThrowAnyException()
        );
    }

    @Test
    @DisplayName("이름을 입력하지 않은 상태로 생성하면 이름은 '익명의 사용자'가 된다.")
    void create_success_when_not_insert_name() {
        final UserEntity user = new UserEntity("hello@hello.com", "12345678");
        assertThat(user.getName()).isEqualTo("익명의 사용자");
    }

    @Test
    @DisplayName("email이나 password가 null이면 예외가 발생한다.")
    void create_fail_email_or_password_is_null() {
        assertAll(
                () -> assertThatThrownBy(() -> new UserEntity(null, "12345678", "디투"))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new UserEntity("email@email.com", null, "디투"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("name이 null이면 예외가 발생한다.")
    void create_fail_name_is_null() {
        assertThatThrownBy(() -> new UserEntity("email@email.com", "12345678", null))
                .isInstanceOf(RuntimeException.class);
    }
}