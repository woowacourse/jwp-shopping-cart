package woowacourse.shoppingcart.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerWithIdTest {

    @DisplayName("id가 null이라면 로그인이 안된 유저라고 생각해서 true를 반환한다.")
    @Test
    void isNotLogin() {
        CustomerWithId customerWithId = new CustomerWithId(null);
        assertThat(customerWithId.isNotLogin()).isTrue();
    }

    @DisplayName("id가 null이 아니라면 로그인이 된 유저라고 생각해서 false를 반환한다.")
    @Test
    void isLogin() {
        CustomerWithId customerWithId = new CustomerWithId(2L);
        assertThat(customerWithId.isNotLogin()).isFalse();
    }
}
