package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @Test
    void 비밀번호가_null인_경우() {
        assertThatThrownBy(() -> new Password(null)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 비밀번호는 빈 값일 수 없습니다.");
    }

    @Test
    void 비밀번호가_빈_칸인_경우() {
        assertThatThrownBy(() -> new Password("")).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 비밀번호는 빈 값일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ㄱ", "ㅣ", "기", "긱", " abc", "a bc", "abc ", "ABCㄱ", "ㄱ123", "12ㄱ3"})
    void 비밀번호에_한글이나_공백이_있는_경우(String invalidPassword) {
        assertThatThrownBy(() -> new Password(invalidPassword)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 비밀번호는 한글이나 공백이 들어갈 수 없습니다.");
    }

    @Test
    void 비밀번호가_6자_미만인_경우() {
        String invalidPassword = "aaaaa";
        assertThatThrownBy(() -> new Password(invalidPassword)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 비밀번호는 최소 6자 이상이어야 합니다.");
    }

    @Test
    void 비밀번호가_같은_경우() {
        String rawPassword = "aaaaaa";
        String encodedPassword = new Password(rawPassword).generateEncodedPassword();
        assertThat(new Password(encodedPassword).isSamePassword(new Password("aaaaaa"))).isTrue();
    }
}
