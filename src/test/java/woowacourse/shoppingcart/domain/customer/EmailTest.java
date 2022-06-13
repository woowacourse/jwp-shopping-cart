package woowacourse.shoppingcart.domain.customer;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("email null이면 예외를 던진다.")
    @Test
    void create_error_null() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("이메일은 필수 입력 사항입니다.");
    }

    @DisplayName("이메일 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"@email.com", "test@", "testemail.com", "test@email", "email"})
    void create_error_emailFormat(String email) {
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다. (형식: example@email.com)");
    }

    @DisplayName("email 형식에 맞으면 email이 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {MAT_EMAIL, YAHO_EMAIL})
    void create(String email) {
        assertDoesNotThrow(() -> new Email(email));
    }
}
