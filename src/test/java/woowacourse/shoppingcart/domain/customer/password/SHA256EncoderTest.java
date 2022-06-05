package woowacourse.shoppingcart.domain.customer.password;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SHA256EncoderTest {

    @DisplayName("인코딩을 거치면 기존의 password와 다른 password가 반환된다.")
    @Test
    void encode() {
        RawPassword rawPassword = new RawPassword("q1w2e3r4!");

        SHA256Encoder encoder = new SHA256Encoder();
        EncodedPassword encodedPassword = encoder.encode(rawPassword);

        assertThat(rawPassword.getValue()).isNotEqualTo(encodedPassword.getValue());
    }

    @DisplayName("같은 password 를 인코딩하면 항상 같은 password 가 반환된다.")
    @Test
    void encode_alwaysSame() {
        RawPassword rawPassword = new RawPassword("q1w2e3r4!");

        SHA256Encoder encoder = new SHA256Encoder();
        EncodedPassword firstEncodedPassword = encoder.encode(rawPassword);
        EncodedPassword secondEncodedPassword = encoder.encode(rawPassword);

        assertThat(firstEncodedPassword).isEqualTo(secondEncodedPassword);
    }

    @DisplayName("인코딩 전 password 가 인코딩 된 password 와 같은지 확인한다.")
    @Test
    void matches() {
        RawPassword rawPassword = new RawPassword("q1w2e3r4!");

        SHA256Encoder encoder = new SHA256Encoder();
        EncodedPassword encodedPassword = encoder.encode(rawPassword);

        assertThat(encoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}
