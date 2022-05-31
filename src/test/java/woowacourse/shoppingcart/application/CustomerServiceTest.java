package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {

    private final CustomerService customerService;

    @Autowired
    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DisplayName("이메일이 중복되는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, false", "distinctemail@email.com, true"})
    void isDistinctEmail(final String email, final Boolean expected) {
        assertThat(customerService.isDistinctEmail(email)).isEqualTo(expected);
    }
}
