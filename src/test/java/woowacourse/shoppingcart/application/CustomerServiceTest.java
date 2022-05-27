package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // when
        Customer customer = customerService.save(customerRequest);

        // then
        assertAll(
            () -> assertThat(customer.getId()).isNotNull(),
            () -> assertThat(customer.getUsername().getValue()).isEqualTo(customerRequest.getUsername()),
            () -> assertThat(customer.getPassword().getValue()).isEqualTo(customerRequest.getPassword()),
            () -> assertThat(customer.getPhoneNumber().getValue()).isEqualTo(customerRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(customerRequest.getAddress())
        );
    }
}
