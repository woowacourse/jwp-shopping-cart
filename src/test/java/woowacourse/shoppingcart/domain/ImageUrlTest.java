package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageUrlTest {

    @DisplayName("이미지 주소 길이가 2000자 이하이면 이미지 주소를 생성한다.")
    @Test
    void makeImageUrl() {
        final String value = "a".repeat(2000);

        assertThat(new ImageUrl(value)).isNotNull();
    }

    @DisplayName("이미지 주소가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenNullOrEmpty(String imageUrl) {
        assertThatThrownBy(() -> new ImageUrl(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 주소는 비어있을 수 없습니다.");
    }

    @DisplayName("이미지 주소의 길이가 2000자를 초과하면 예외를 발생한다.")
    @Test
    void throwWhenLength() {
        final String imageUrl = "a".repeat(2001);
        assertThatThrownBy(() -> new ImageUrl(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 주소는 2000자를 초과할 수 없습니다.");
    }
}
