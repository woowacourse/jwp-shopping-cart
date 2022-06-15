package woowacourse.shoppingcart.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.value.Email;

class EmailTest {

    @DisplayName("Email 규칙에 맞는 문자열로 Email을 생성한다.")
    @Test
    void create() {
        String value = "abc@naver.com";

        Email email = new Email(value);

        assertThat(email.getValue()).isEqualTo("abc@naver.com");
    }

    @DisplayName("Email 규칙에 맞지 않는 문자열이 들어오면 예외를 반환한다.")
    @Test
    void create_InvalidEmailFormat() {
        String invalid = "aaaaa";

        assertThatThrownBy(() -> new Email(invalid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

}
