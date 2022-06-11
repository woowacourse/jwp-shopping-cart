package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_2;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcAddressDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.JdbcPrivacyDao;
import woowacourse.shoppingcart.dao.PrivacyDao;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.EmailDuplicationResponse;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;
import woowacourse.shoppingcart.repository.CustomerRepository;

@JdbcTest
class CustomerServiceTest {
    private final CustomerService customerService;

    @Autowired
    public CustomerServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        CustomerDao customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        PrivacyDao privacyDao = new JdbcPrivacyDao(jdbcTemplate, dataSource);
        AddressDao addressDao = new JdbcAddressDao(jdbcTemplate, dataSource);
        CustomerRepository customerRepository = new CustomerRepository(customerDao, privacyDao, addressDao);

        this.customerService = new CustomerService(customerRepository);
    }

    @DisplayName("customer를 생성한다.")
    @Test
    void create() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // when & then
        assertThat(id).isNotNull();
    }

    @DisplayName("email 중복 여부를 반환한다.")
    @Test
    void checkDuplicatedEmail() {
        // given
        customerService.create(CUSTOMER_REQUEST_1);

        // when
        EmailDuplicationResponse response = customerService.isDuplicatedEmail(CUSTOMER_REQUEST_1.getEmail());

        // then
        assertThat(response.getIsDuplicated()).isTrue();
    }

    @DisplayName("Customer Id를 통해 해당 유저의 정보를 조회할 수 있다.")
    @Test
    void getCustomer() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // when
        CustomerResponse customerResponse = customerService.getCustomerById(id);

        // then
        assertThat(customerResponse).extracting("email", "profileImageUrl", "terms")
                .containsExactly(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getProfileImageUrl(),
                        CUSTOMER_REQUEST_1.isTerms());
    }

    @DisplayName("해당 유저의 정보를 조회할 때 Customer Id가 없으면 예외가 발생한다.")
    @Test
    void getCustomer_throwsException() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // when & then
        assertThatThrownBy(() -> customerService.getCustomerById(id + 1))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("Customer Id를 통해 해당 유저의 정보를 수정할 수 있다.")
    @Test
    void updateCustomer() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // when
        customerService.updateCustomerById(id, CUSTOMER_REQUEST_2);
        CustomerResponse customerResponse = customerService.getCustomerById(id);

        // then
        assertThat(customerResponse).extracting("profileImageUrl", "name", "gender", "contact")
                .containsExactly(CUSTOMER_REQUEST_2.getProfileImageUrl(), CUSTOMER_REQUEST_2.getName(),
                        CUSTOMER_REQUEST_2.getGender(), CUSTOMER_REQUEST_2.getContact());
    }

    @DisplayName("존재하지 않는 유저를 수정하면 예외가 발생한다.")
    @Test
    void updateCustomer_throwsException() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // then
        assertThatThrownBy(() -> customerService.updateCustomerById(id + 1, CUSTOMER_REQUEST_2))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("Customer Id를 통해 해당 유저를 삭제할 수 있다.")
    @Test
    void deleteCustomer() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // when
        customerService.deleteCustomer(id);

        // then
        assertThatThrownBy(() -> customerService.getCustomerById(id))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("존재하지 않는 유저를 삭제하면 예외가 발생한다.")
    @Test
    void deleteCustomer_throwsException() {
        // given
        long id = customerService.create(CUSTOMER_REQUEST_1);

        // then
        assertThatThrownBy(() -> customerService.deleteCustomer(id + 1))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
