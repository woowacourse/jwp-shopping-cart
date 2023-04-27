package cart.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {

    @ParameterizedTest(name = "1~10자리의 문자열은 name으로 가능하다.")
    @ValueSource(strings = {"1", "12345qwert"})
    void init_inRange_notThrow(String value) {
        assertThatCode(() -> new Name(value)).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "1~10자리가 아닌 문자열은 name으로 불가능하다.")
    @ValueSource(strings = {"", "123456qwert"})
    void init_outRange_throw(String value) {
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
