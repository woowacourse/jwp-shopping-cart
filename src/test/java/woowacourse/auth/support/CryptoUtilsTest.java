package woowacourse.auth.support;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CryptoUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"Qwer1234!", "Asdf6788@"})
    @DisplayName("비밀번호를 단방향으로 해싱할 수 있다.")
    void encrypt(String password) {
        // given
        String encryptedPassword = CryptoUtils.encrypt(password);

        // when
        String encryptedWithSamePassword = CryptoUtils.encrypt(password);

        // then
        Assertions.assertThat(encryptedWithSamePassword).isEqualTo(encryptedPassword);
    }
}
