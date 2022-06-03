package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.vo.Account;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.EncryptPassword;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CustomerTest {

    @Test
    void 고객_생성() {
        Account account = new Account("yhh1056");
        Nickname nickname = new Nickname("호호");
        EncryptPassword password = new EncryptPassword("gusghWkd12!");
        Address address = new Address("호호네");
        PhoneNumber phoneNumber = new PhoneNumber("010", "1234", "5678");
        Customer customer = new Customer(account, nickname, password, address, phoneNumber);

        assertThat(customer).isNotNull();
    }

}
