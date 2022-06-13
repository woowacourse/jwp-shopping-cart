package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.values.Address;
import woowacourse.shoppingcart.domain.customer.values.PhoneNumber;
import woowacourse.shoppingcart.domain.customer.values.Username;
import woowacourse.shoppingcart.domain.customer.values.password.EncryptedPassword;

class CustomerTest {

    @Test
    @DisplayName("빌더 패턴 객체 생성")
    void customer_builder() {
        Long id = 1L;
        String username = "username1";
        String password = "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f";
        String phoneNumber = "01012345678";
        String address = "성담빌딩";

        Customer customer = Customer.builder()
                .id(id)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();

        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(new Customer(id, new Username(username), new EncryptedPassword(password),
                        new PhoneNumber(phoneNumber), new Address(address)));
    }
}
