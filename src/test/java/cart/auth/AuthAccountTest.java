package cart.auth;

import cart.global.exception.auth.InvalidEmailFormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthAccountTest {

    private final String password = "password";

    @ParameterizedTest
    @ValueSource(strings = {"example@", "example", "example#example.com"})
    @DisplayName("constructor : 이메일 형식이 잘못됐을 경우 InvalidEmailFormatException이 발생한다.")
    void test_constructor_InvalidEmailFormatException(final String email) throws Exception {
        //when & then
        Assertions.assertThatThrownBy(() -> new AuthAccount(email, password))
                  .isInstanceOf(InvalidEmailFormatException.class);
    }

    @Test
    @DisplayName("constructor : 올바른 이메일 형태를 통해 AuthAccount를 만들 수 있다.")
    void test_constructor() throws Exception {
        //given
        final String email = "example@example.com";

        //when
        final AuthAccount authAccount = new AuthAccount(email, password);

        //then
        assertAll(
                () -> assertEquals(email, authAccount.getEmail()),
                () -> assertEquals(password, authAccount.getPassword())
        );
    }
}
