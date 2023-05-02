package cart.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UrlTest {

    @Test
    @DisplayName("올바르지 않은 URL 인 경우, 예외를 발생시킨다.")
    void createUrlFail() {
        assertThatThrownBy(() -> Url.of(""))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("올바르지 않은 링크입니다.");
    }

    @Test
    @DisplayName("올바른 URL 인 경우, 객체를 생성한다.")
    void createUrlSuccess() {
        String validLink = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0ZReQ5GXoMIwMn5vMIHhRi2VlQoSPAL5d4w&usqp=CAU";

        Url url = Url.of("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0ZReQ5GXoMIwMn5vMIHhRi2VlQoSPAL5d4w&usqp=CAU");

        assertThat(url.getValue()).isEqualTo(validLink);
    }

}
