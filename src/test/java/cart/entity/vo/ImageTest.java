package cart.entity.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


class ImageTest {
    @Test
    @DisplayName("url 형식으로 넣을때 성공인지 테스트")
    void SuccessTest() {
        Assertions.assertThatNoException().isThrownBy(() -> {
            new Image("https://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg");
        });
    }

    @ParameterizedTest(name = "url 형식이 아닐때 실패 테스트")
    @MethodSource("invalidProvider")
    void FailTest(final String testUrl) {
        Assertions.assertThatThrownBy(() -> {
            new Image(testUrl);
        }).hasMessage("url 형식이 아닙니다.");
    }

    private static Stream<Arguments> invalidProvider() {
        return Stream.of(
                Arguments.of("htt://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg"),
                Arguments.of("pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg"),
                Arguments.of("https:pbs.twimg//profile_images/1374979417915547648/vKspl9Et_400x400")
        );
    }

}
