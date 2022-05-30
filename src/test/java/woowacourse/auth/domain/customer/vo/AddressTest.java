package woowacourse.auth.domain.customer.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AddressTest {

    @ParameterizedTest(name = "주소 : {0}")
    @ValueSource(strings = {" 호호네 ", " 호호네", "호호네 "})
    void 앞뒤_공백_제거_후_생성(String value) {
        Address address = new Address(value);
        Address expectedAddress = new Address("호호네");

        assertThat(address).isEqualTo(expectedAddress);
    }

    @Test
    void 최대길이를_초과한_주소_생성_예외() {
        String value = "호".repeat(256);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Address(value))
                .withMessage(String.format("주소는 최대 255자까지 가능합니다. 입력값 : %s", value));
    }

    @ParameterizedTest(name = "주소 : {0}")
    @EmptySource
    void 빈_값_생성_예외(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Address(value))
                .withMessage(String.format("주소는 빈 값 생성이 불가능합니다. 입력값 : %s", value));
    }
}
