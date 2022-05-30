package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EncryptPasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new EncryptPasswordEncoder();

    @Test
    @DisplayName("비밀번호를 인코딩한다.")
    void encode() {
        String password = "password";

        assertThat(passwordEncoder.encode(password)).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {"password,true", "error,false"})
    @DisplayName("패스워드가 동일한지 확인한다.")
    void isMatchPassword(String password, boolean expected) {
        String encodedPassword = passwordEncoder.encode("password");

        assertThat(passwordEncoder.isMatchPassword(encodedPassword, password)).isEqualTo(expected);
    }
}
