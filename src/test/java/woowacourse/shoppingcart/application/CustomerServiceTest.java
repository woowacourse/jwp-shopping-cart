package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        CustomerDao customerDao = new CustomerDao(jdbcTemplate);
        customerService = new CustomerService(customerDao);
    }

    @DisplayName("유저 생성 정보를 입력 받아, 회원가입을 한다.")
    @Test
    void createCustomer() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest ("beomWhale@naver.com", "범고래", "Password12345!");

        // when
        Long savedId = customerService.createCustomer(customerCreateRequest);

        // then
        assertThat(savedId).isNotNull();
    }
}
