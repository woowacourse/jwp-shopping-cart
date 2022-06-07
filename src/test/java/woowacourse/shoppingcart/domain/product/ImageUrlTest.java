package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidFormException;

class ImageUrlTest {

    @Test
    @DisplayName("이미지 url이 http로 시작하지 않는 경우, 예외를 발생한다.")
    void invalidStartWithHttpException() {
        assertThatExceptionOfType(InvalidFormException.class)
                .isThrownBy(() -> new ImageUrl("httt://abcd"));
    }

    @Test
    @DisplayName("이미지 url을 생성한다.")
    void createImageUrl() {
        String value = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMjJfMjE4%2FMDAxNjEzOTIzNTY4NTUz.dladUcTemSByKMV6UZQIeQ2Q0uZPmEY-QSaCZziy2tMg.12F4zUdWqGZGEGdMLTYhm7tsRuSUe39mYYPIQpJf0vAg.PNG.looh2040%2F%25C8%25AD%25B8%25E9_%25C4%25B8%25C3%25B3_2021-02-22_005905-removebg-preview_%25281%2529.png&type=sc960_832";
        ImageUrl imageUrl = new ImageUrl(value);

        assertThat(imageUrl.getValue()).isEqualTo(value);
    }
}
