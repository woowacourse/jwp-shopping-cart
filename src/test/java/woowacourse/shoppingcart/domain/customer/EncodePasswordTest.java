package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.auth.support.HashPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class EncodePasswordTest {

    @DisplayName("비밀번호 길이가 암호화된 길이가 아니면 예외를 발생시킨다.")
    @Test
    void validatePassword() {
        String expected = encode("kth@990303").getPassword();
        assertThat(expected.length()).isEqualTo(64);
    }

    @DisplayName("비밀번호 일치 여부를 확인한다.")
    @ParameterizedTest(name = "{0}")
    @CsvSource({"kth@990303, true", "forky@123, false"})
    void hasSamePassword(String password, boolean expected) {
        EncodePassword given = encode("kth@990303");
        assertThat(given.hasSamePassword(encode(password))).isEqualTo(expected);
    }

    private EncodePassword encode(String rawPassword) {
        RawPassword password = new RawPassword(rawPassword);
        PasswordEncoder passwordEncoder = new HashPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}