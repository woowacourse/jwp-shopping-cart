package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    @DisplayName("이름을 생성한다.")
    void createName() {
        String value = "slow";

        assertThatCode(() -> new Name(value))
                .doesNotThrowAnyException();
    }
}
