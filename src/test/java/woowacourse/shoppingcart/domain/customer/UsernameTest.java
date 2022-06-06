package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsernameTest {

    @DisplayName("username이 null이면 예외를 던진다.")
    @Test
    void create_error_null() {
        assertThatThrownBy(() -> new Username(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("유저 네임은 필수 입력 사항입니다.");
    }

    @DisplayName("username 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", "testtesttesttesttesttest", "TESTTEST", "test.test"})
    void create_error_usernameFormat(String username) {
        assertThatThrownBy(() -> new Username(username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저 네임 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능, 5자 이상 20자 이내)");
    }

    @DisplayName("username 형식에 맞으면 username이 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"pup-paw", "pup_paw", "123456", "a1b2c3_-"})
    void create(String username) {
        assertDoesNotThrow(() -> new Username(username));
    }
}
