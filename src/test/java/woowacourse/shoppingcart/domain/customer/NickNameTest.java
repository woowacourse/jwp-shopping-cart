package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class NickNameTest {

    @DisplayName("닉네임이 10글자를 초과하면 예외가 발생한다")
    @Test
    void construct_over_10() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new NickName("기똥차고멋들어진포키짱"))
                .withMessageContaining("10자 이하");
    }

    @DisplayName("닉네임이 공백이면 예외가 발생한다")
    @Test
    void construct_blank() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new NickName(" "))
                .withMessageContaining("공백");
    }
}