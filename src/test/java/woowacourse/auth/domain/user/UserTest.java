package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.domain.user.privacy.Privacy;
import woowacourse.auth.exception.DisagreeToTermsException;

class UserTest {
    @DisplayName("유저 정보를 전달하여 유저를 생성한다.")
    @Test
    void of() {
        // given
        String email = "devhudi@gmail.com";
        String password = "a!123456";
        String profileImageUrl = "http://gravatar.com/avatar/1?d=identicon";
        Privacy privacy = Privacy.of("조동현", "male", "1998-12-21", "01011111111", "서울특별시 강남구 선릉역", "이디야 1층", "12345");
        boolean terms = true;

        // when
        User actual = User.of(email, password, profileImageUrl, privacy, terms);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("약관 동의 여부가 거짓이라면 예외가 발생한다.")
    @Test
    void of_termsIsFalse() {
        // given
        String email = "devhudi@gmail.com";
        String password = "a!123456";
        String profileImageUrl = "http://gravatar.com/avatar/1?d=identicon";
        Privacy privacy = Privacy.of("조동현", "male", "1998-12-21", "01011111111", "서울특별시 강남구 선릉역", "이디야 1층", "12345");
        boolean terms = false;

        // when & then
        assertThatThrownBy(() -> User.of(email, password, profileImageUrl, privacy, terms))
                .isInstanceOf(DisagreeToTermsException.class);
    }
}
