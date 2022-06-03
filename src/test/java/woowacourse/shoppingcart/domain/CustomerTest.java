package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.domain.customer.Customer;

class CustomerTest {

    @Test
    @DisplayName("회원을 생성한다.")
    void createCustomer() {
        assertThatCode(() -> Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111"))
            .doesNotThrowAnyException();
    }
}
