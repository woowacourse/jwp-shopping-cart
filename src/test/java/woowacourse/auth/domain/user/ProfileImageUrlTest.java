package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.format.InvalidUrlFormatException;

class ProfileImageUrlTest {

    @DisplayName("프로필 이미지 URL 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String url = "http://gravatar.com/avatar/1?d=identicon";

        // when
        ProfileImageUrl actual = new ProfileImageUrl(url);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 URL 형식이 전달되었을때 예외가 발생한다.")
    @ValueSource(strings = {"", "naver.com", "http://naver.", "naver"})
    @ParameterizedTest
    void constructor_invalidUrlFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new ProfileImageUrl(input))
                .isInstanceOf(InvalidUrlFormatException.class);
    }
}
