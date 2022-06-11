package woowacourse.shoppingcart.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LookUpUserTest {

    @DisplayName("id가 null이라면 로그인이 안된 유저라고 생각해서 true를 반환한다.")
    @Test
    void isNotLogin() {
        LookUpUser lookUpUser = new LookUpUser(null);
        assertThat(lookUpUser.isNotLogin()).isTrue();
    }

    @DisplayName("id가 null이 아니라면 로그인이 된 유저라고 생각해서 false를 반환한다.")
    @Test
    void isLogin() {
        LookUpUser lookUpUser = new LookUpUser(2L);
        assertThat(lookUpUser.isNotLogin()).isFalse();
    }
}
