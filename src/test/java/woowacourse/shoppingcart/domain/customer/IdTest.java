package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IdTest {

    @DisplayName("빈 Id 객체의 값을 가져올 때 예외를 발생시킨다.")
    @Test
    void getValueThrowsExceptionIfEmpty() {
        //given
        Id empty = Id.empty();

        //when & then
        assertThatThrownBy(empty::getValue).isInstanceOf(NullPointerException.class)
                .hasMessage("해당 객체는 id를 가지고 있지 않습니다.");
    }
}
