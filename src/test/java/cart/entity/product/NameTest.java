package cart.entity.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @ParameterizedTest(name = "상품명이 {0}일 떄")
    @NullAndEmptySource
    @DisplayName("상품명이 존재하지 않을 경우 오류를 던진다.")
    void nameNotExist(final String value) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명이 50자 초과일 경우 오류를 던진다")
    void nameOverLength() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Name("1".repeat(51)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명을 조회한다.")
    void getValue() {
        //given
        final Name name = new Name("validName");

        //when
        //then
        assertThat(name.getValue()).isEqualTo("validName");
    }
}
