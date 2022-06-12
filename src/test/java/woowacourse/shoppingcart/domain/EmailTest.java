package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {

    @Test
    void 이메일이_null인_경우() {
        assertThatThrownBy(() -> new Email(null)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 이메일은 빈 값일 수 없습니다.");
    }

    @Test
    void 이메일이_빈_칸인_경우() {
        assertThatThrownBy(() -> new Email("")).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 이메일은 빈 값일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "bcc0830naver.com", "bcc0830@", "bcc0830", "bcc0830#naver.com",
            "bcc0830@navercom", "bcc0830@ naver.com", " bcc0830@naver.com", "bcc0830@naver.com "})
    void 유효하지_않은_이메일인_경우(String invalidEmail) {
        assertThatThrownBy(() -> new Email(invalidEmail)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 이메일 형식이 아닙니다.");
    }

    @Test
    void 이메일이_64자_초과인_경우() {
        String invalidEmail = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@naver.com";
        assertThatThrownBy(() -> new Email(invalidEmail)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 이메일은 최대 64자 이하여야 합니다.");
    }
}
