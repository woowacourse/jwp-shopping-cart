package woowacourse.auth.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.EncodePassword;
import woowacourse.shoppingcart.domain.customer.RawPassword;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HashPasswordEncoderTest {

    private static final int ENCODE_PASSWORD_LENGTH = 64;

    private final HashPasswordEncoder hashPasswordEncoder = new HashPasswordEncoder();

    @DisplayName("비밀번호를 암호화한다.")
    @Test
    void encode() {
        RawPassword rawPassword = new RawPassword("kth@12345");

        EncodePassword encodePassword = hashPasswordEncoder.encode(rawPassword);

        assertAll(
                () -> assertThat(encodePassword.getPassword()).isNotEqualTo(rawPassword.getPassword()),
                () -> assertThat(encodePassword.getPassword().length()).isEqualTo(ENCODE_PASSWORD_LENGTH)
        );
    }
}