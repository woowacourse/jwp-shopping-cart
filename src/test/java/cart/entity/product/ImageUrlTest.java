package cart.entity.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageUrlTest {

    @ParameterizedTest(name = "이미지 경로가 {0}일 떄")
    @NullAndEmptySource
    @DisplayName("이미지 경로가 존재하지 않을 경우 오류를 던진다.")
    void imageUrlNotExist(final String value) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new ImageUrl(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지 경로를 조회한다.")
    void getValue() {
        //given
        final ImageUrl imageUrl = new ImageUrl("validName");

        //when
        //then
        assertThat(imageUrl.getValue()).isEqualTo("validName");
    }
}
