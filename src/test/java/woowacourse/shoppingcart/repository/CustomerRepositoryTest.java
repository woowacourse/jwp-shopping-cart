package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.dto.TokenRequest;
import woowacourse.shoppingcart.exception.ResourceNotFoundException;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;


@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
class CustomerRepositoryTest {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerRepositoryTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.customerRepository = new CustomerRepository(
                new CustomerDao(namedParameterJdbcTemplate),
                new CartItemDao(namedParameterJdbcTemplate)
        );
    }

    @DisplayName("customer 를 DB에 저장하고 아이디로 customer 를 찾는다.")
    @Test
    void createAndFindById() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "abcde123!", "jojogreen");

        // when
        Long id = customerRepository.insert(customer);

        // then
        Customer createdCustomer = customerRepository.selectById(id);
        assertThat(createdCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @DisplayName("입력된 비밀번호를 대조하고 로그인한 customer 를 반환한다.")
    @Test
    void login() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "1234abcd!", "jojogreen");
        customerRepository.insert(customer);

        // when
        Customer loginCustomerResult = customerRepository.selectByUsernameAndPassword("jo@naver.com", "1234abcd!");

        // then
        assertThat(loginCustomerResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @DisplayName("해당 아이디의 회원정보를 변경한다.")
    @Test
    void update() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "1234abcd!", "jojogreen");
        Long id = customerRepository.insert(customer);
        Customer newCustomer = new Customer(id, "jo@naver.com", "1234abcd!", "hunch");

        // when
        customerRepository.update(newCustomer);

        // then
        assertThat(customerRepository.selectById(id))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newCustomer);
    }

    @DisplayName("해당 아이디의 비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "1234abcd!", "jojogreen");
        Long id = customerRepository.insert(customer);
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest("1234abcd!", "1234abcd@");
        Password oldPassword = new Password(passwordChangeRequest.getOldPassword());
        Password newPassword = new Password(passwordChangeRequest.getNewPassword());

        // when
        customerRepository.updatePassword(id, oldPassword, newPassword);

        // then
        assertThat(customerRepository.selectById(id).getPassword())
                .isEqualTo(newPassword.getPassword());
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        // given
        Customer customer = Customer.ofNullId("jo@naver.com", "1234abcd!", "jojogreen");
        Long id = customerRepository.insert(customer);
        TokenRequest tokenRequest = new TokenRequest(id);

        // when
        customerRepository.delete(tokenRequest.getId());

        // then
        assertThatThrownBy(() -> customerRepository.selectById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
