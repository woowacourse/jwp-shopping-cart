package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageUrlTest {

    @DisplayName("이미지 URL을 입력받아 정상 생성한다")
    @Test
    void create() {
        //when
        String value = "https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg";
        ImageUrl imageUrl = new ImageUrl(value);
        //then
        assertThat(imageUrl.getValue()).isEqualTo(value);
    }

    @DisplayName("이미지 URL에 빈 값을 입력하면 예외를 반환한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createExceptionWithBlank(final String value) {
        //then
        assertThatThrownBy(() -> new ImageUrl(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미지 URL에 5000자 이상 값을 입력하면 예외를 반환한다")
    @Test
    void createExceptionWithOver5000() {
        //given
        String value = "1".repeat(5001);
        //then
        assertThatThrownBy(() -> new ImageUrl(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
