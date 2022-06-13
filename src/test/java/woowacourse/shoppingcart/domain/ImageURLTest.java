package woowacourse.shoppingcart.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageURLTest {

    @ParameterizedTest
    @ValueSource(strings = {"https:/www.naver.com", "https", "httpa://www.naver.com", " http://www.naver.com",
                            "https://www.naver.com ", "https://www.naver .com", "https//www.naver.com",
                            "https://www.nav er.com", "https://ww w.naver.com"})
    void URL_주소형식이_올바르지_않은_경우(String invalidURL) {
        assertThatThrownBy(() -> new ImageURL(invalidURL)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] URL 형식이 올바르지 않습니다.");
    }
}
