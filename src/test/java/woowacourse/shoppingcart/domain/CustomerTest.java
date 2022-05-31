package woowacourse.shoppingcart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @DisplayName("Customer를 생성하면, 비밀번호가 암호화 된다.")
    @Test
    void hashPassword() {
        //given
        final String password = "qwer123";

        //when
        final Customer customer = new Customer("kun", "kun@email.com", password);
        final String actual = customer.getPassword();

        //then
        Assertions.assertThat(actual).isNotEqualTo(password);
    }
}
