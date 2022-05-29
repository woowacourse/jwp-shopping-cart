package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    @DisplayName("주소를 생성한다.")
    void createAddress() {
        String value = "루터회관 14층";

        assertThatCode(() -> new Address(value))
                .doesNotThrowAnyException();
    }
}
