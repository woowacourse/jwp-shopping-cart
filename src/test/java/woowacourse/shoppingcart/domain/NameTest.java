package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @DisplayName("이름이 정상적일 경우 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"김승래", "승팡", "seungpapang"})
    void createName(String name) {
        Name CustomerName = new Name(name);

        assertThat(CustomerName.getValue()).isEqualTo(name);
    }

    @DisplayName("이름의 형식이 유효하지 않을 경우 예외발생.")
    @ParameterizedTest
    @ValueSource(strings = {"asdas!@#!@#", "....", "!@$!@$"})
    void throwExceptionValidate(String name) {
        assertThatThrownBy(() -> new Name(name))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
