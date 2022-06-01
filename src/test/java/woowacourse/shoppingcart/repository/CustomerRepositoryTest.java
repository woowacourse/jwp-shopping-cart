package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.repository.dao.CustomerDao;


@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CustomerRepositoryTest {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRepositoryTest(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.customerRepository = new CustomerRepository(new CustomerDao(jdbcTemplate, namedParameterJdbcTemplate));
    }

    @DisplayName("customer 를 DB에 저장하고 아이디로 customer 를 찾는다.")
    @Test
    void createAndFindById() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "abcde123!", "jojogreen", false);

        // when
        Long id = customerRepository.create(customer);

        // then
        Customer createdCustomer = customerRepository.findById(id);
        assertThat(createdCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @DisplayName("입력된 비밀번호를 대조하고 로그인한 customer 를 반환한다.")
    @Test
    void login() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "1234abcd!", "jojogreen", false);
        customerRepository.create(customer);

        // when
        Customer loginCustomerResult = customerRepository.login("jo@naver.com", "1234abcd!");

        // then
        assertThat(loginCustomerResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }
}
