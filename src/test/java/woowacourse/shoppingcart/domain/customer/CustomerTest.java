package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.vo.Account;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CustomerTest {

    @Test
    void 고객_생성() {
        Customer customer = new Customer(null,
                new Account("yhh1056"),
                new Nickname("호호"),
                Password.plainText("gusghWkd12!"),
                new Address("호호네"),
                new PhoneNumber("010", "1234", "5678"));

        assertThat(customer).isNotNull();
    }
}
