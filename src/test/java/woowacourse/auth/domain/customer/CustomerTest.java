package woowacourse.auth.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import woowacourse.auth.domain.customer.privacy.Privacy;
import woowacourse.auth.exception.DisagreeToTermsException;

class CustomerTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("유저 정보를 전달하여 유저를 생성한다.")
    @Test
    void constructor() {
        // given
        Email email = new Email("devhudi@gmail.com");
        Password password = Password.fromPlainText("a!123456", passwordEncoder);
        ProfileImageUrl profileImageUrl = new ProfileImageUrl("http://gravatar.com/avatar/1?d=identicon");
        Privacy privacy = Privacy.of("조동현", "male", "1998-12-21", "01011111111", "서울특별시 강남구 선릉역", "이디야 1층", "12345");
        boolean terms = true;

        // when
        Customer actual = new Customer(email, password, profileImageUrl, privacy, terms);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("약관 동의 여부가 거짓이라면 예외가 발생한다.")
    @Test
    void of_termsIsFalse() {
        // given
        Email email = new Email("devhudi@gmail.com");
        Password password = Password.fromPlainText("a!123456", passwordEncoder);
        ProfileImageUrl profileImageUrl = new ProfileImageUrl("http://gravatar.com/avatar/1?d=identicon");
        Privacy privacy = Privacy.of("조동현", "male", "1998-12-21", "01011111111", "서울특별시 강남구 선릉역", "이디야 1층", "12345");
        boolean terms = false;

        // when & then
        assertThatThrownBy(() -> new Customer(email, password, profileImageUrl, privacy, terms))
                .isInstanceOf(DisagreeToTermsException.class);
    }
}
