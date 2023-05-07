package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    static Stream<Arguments> userNormalFieldDummy() {
        return Stream.of(
                Arguments.of(1L, new Email("test1@email.com"), new Password("test1Pw")),
                Arguments.of(null, new Email("test1@email.com"), new Password("test1Pw"))
        );
    }

    @DisplayName("아이디, 이메일, 전화번호를 입력받아 생성한다. 아이디는 null 허용")
    @ParameterizedTest
    @MethodSource("userNormalFieldDummy")
    void create(final Long id, final Email email, final Password password) {
        //when
        User user = new User.Builder()
                .id(id)
                .email(email)
                .password(password)
                .build();
        //then
        assertThat(user).isNotNull();
    }

    @DisplayName("이메일이 null일 경우 예외를 반환한다")
    @Test
    void createExceptionWithNullEmail() {
        //then
        assertThatThrownBy(() -> new User.Builder()
                .id(1L)
                .email(null)
                .password(new Password("testPW"))
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호가 null일 경우 예외를 반환한다")
    @Test
    void createExceptionWithNullPassword() {
        //then
        assertThatThrownBy(() -> new User.Builder()
                .id(1L)
                .email(new Email("test@email.com"))
                .password(null)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
