package cart.entity.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DescriptionTest {

    @Test
    @DisplayName("상품 설명이 255자 초과일 경우 오류를 던진다")
    void descriptionOverLength() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Description("1".repeat(256)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 설명을 조회한다.")
    void getValue() {
        //given
        final Description description = new Description("valid description");

        //when
        //then
        assertThat(description.getValue()).isEqualTo("valid description");
    }
}
