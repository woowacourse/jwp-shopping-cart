package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    void 비밀번호가_일치하는지_확인() {
        Customer customer = new Customer("me", "crew@woowahan.com", "abc1234");
        assertThat(customer.isSamePassword("abc1234")).isTrue();
    }

    @Test
    void 비밀번호가_일치하지않는_경우() {
        Customer customer = new Customer("me", "crew@woowahan.com", "abc1234");
        assertThat(customer.isSamePassword("abc12345")).isFalse();
    }
}
