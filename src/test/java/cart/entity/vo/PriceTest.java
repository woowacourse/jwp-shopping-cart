package cart.entity.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {
    @Test
    @DisplayName("url 형식으로 넣을때 성공인지 테스트")
    void SuccessTest() {
        Assertions.assertThatNoException().isThrownBy(() -> {
            new Price(0);
        });
    }

    @Test
    @DisplayName("price가 0보다 작을때 실패 테스트")
    void FailTest() {
        Assertions.assertThatThrownBy(() -> {
            new Price(-1);
        }).hasMessage("가격은 0보다 작을 수 없습니다");
    }

}
