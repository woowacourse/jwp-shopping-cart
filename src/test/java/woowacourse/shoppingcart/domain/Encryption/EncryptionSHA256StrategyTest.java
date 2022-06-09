package woowacourse.shoppingcart.domain.Encryption;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EncryptionSHA256Strategy 도메인 테스트")
class EncryptionSHA256StrategyTest {

    private final String ORIGINAL = "1234asdf!";
    private final String ENCRYPTION = "1338ad00357397e37ec3990310efd04f767ab485fa8e69f2d06df186f9327372";

    @DisplayName("암호화된 것을 확인한다.")
    @Test
    void encrypt() {
        // given
        Customer customer = Customer.from(1L, "test@woowacourse.com", "test", ORIGINAL);

        // when
        EncryptionStrategy encryptionStrategy = new EncryptionSHA256Strategy();
        customer = encryptionStrategy.encrypt(customer);

        // then
        assertThat(customer.getPassword()).isEqualTo(ENCRYPTION);
    }
}
