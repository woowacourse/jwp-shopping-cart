package woowacourse.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CryptoUtilsTest {

    @DisplayName("서로 다른 두 텍스트를 생성하여 암호화하여 비교하더라도 그 둘의 암호화된 값은 같다")
    @Test
    void equals_after_encrypted_text() {
        // given
        String text_1 = new String("APPLE");
        String text_2 = new String("APPLE");

        // when
        String encryptedText_1 = CryptoUtils.encrypt(text_1);
        String encryptedText_2 = CryptoUtils.encrypt(text_2);

        // then
        // 원본과 암호화된 문자열은 서로 다르다
        assertThat(text_1).isNotEqualTo(encryptedText_1);
        // 동일한 문자열을 각각 암호화한 두 값은 서로 같다
        assertThat(encryptedText_1).isEqualTo(encryptedText_2);
    }
}
