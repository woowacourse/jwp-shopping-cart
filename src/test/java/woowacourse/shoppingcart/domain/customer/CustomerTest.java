package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    @DisplayName("빌더 패턴 객체 생성")
    void customer_builder() {
        // given
        Long id = 1L;
        String username = "username1";
        String password = "password1";
        String phoneNumber = "01012345678";
        String address = "성담빌딩";

        // when
        Customer customer = Customer.builder()
                .id(id)
                .username(username)
                .purePassword(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();

        // then
        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(new Customer(id, new Username(username), Password.purePassword(password), new PhoneNumber(phoneNumber), new Address(address)));
    }
}
