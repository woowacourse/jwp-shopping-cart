package woowacourse.shoppingcart.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncryptionUtilTest {

    @Test
    @DisplayName("암호문과 평문이 기존에 같은 문장이라면 true를 반환한다.")
    void isSameEncryptedPasswordTrue() {
        //given
        String input = "password1!";
        //when
        String encryptedInput = EncryptionUtil.encrypt(input);

        //then
        assertThat(EncryptionUtil.isSameEncryptedPassword(encryptedInput, input)).isTrue();
    }

    @Test
    @DisplayName("암호문과 평문이 기존에 다른 문장이라면 false를 반환한다.")
    void isSameEncryptedPasswordFalse() {
        //given
        String input = "password1!";
        //when
        String encryptedInput = EncryptionUtil.encrypt("password2!");

        //then
        assertThat(EncryptionUtil.isSameEncryptedPassword(encryptedInput, input)).isFalse();
    }
}