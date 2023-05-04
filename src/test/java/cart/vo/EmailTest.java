package cart.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static cart.vo.Email.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("Email 에 잘못된 양식을 집어넣었을 때 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "aaaaaaaaaaaaaaaaaaaaaa@naver.com"})
    void createEmailFail(String input) {
        assertThatThrownBy(() -> Email.from(input))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("올바르지 않은 이메일 형식입니다.");
    }

    @DisplayName("Email 에 제대로 된 양식을 집어넣었을 때 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"a1@naver.com", "aaaaaaaaaaaaaaaaaaaa@naver.com"})
    void createEmailSuccess(String input) {
        Email email = from(input);

        assertThat(email.getValue()).isEqualTo(input);
    }

}
