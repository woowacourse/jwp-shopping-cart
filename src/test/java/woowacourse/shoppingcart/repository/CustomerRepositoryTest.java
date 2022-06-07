package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixtures.헌치;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.exception.custum.ResourceNotFoundException;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;


@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
class CustomerRepositoryTest {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.customerRepository = new CustomerRepository(
                new CustomerDao(jdbcTemplate),
                new CartItemDao(jdbcTemplate));
    }

    @DisplayName("customer 를 DB에 저장하고 아이디로 customer 를 찾는다.")
    @Test
    void createAndFindById() {
        // given
        // when
        Long id = customerRepository.create(헌치);

        // then
        Customer createdCustomer = customerRepository.findById(id);
        assertThat(createdCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(헌치);
    }

    @DisplayName("입력된 비밀번호를 대조하고 로그인한 customer 를 반환한다.")
    @Test
    void login() {
        // given
        customerRepository.create(헌치);

        // when
        Customer loginCustomerResult = customerRepository.findValidUser(헌치.getUsername(), 헌치.getPassword());

        // then
        assertThat(loginCustomerResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(헌치);
    }

    @DisplayName("해당 아이디의 회원정보를 변경한다.")
    @Test
    void update() {
        // given
        Long id = customerRepository.create(헌치);
        Customer 새_헌치 = new Customer(id, 헌치.getUsername(), 헌치.getPassword(), "newHunch");

        // when
        customerRepository.update(새_헌치);

        // then
        assertThat(customerRepository.findById(id))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(새_헌치);
    }

    @DisplayName("해당 아이디의 비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        // given
        Long id = customerRepository.create(헌치);
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest(헌치.getPassword(), "1234abcd@");
        Password oldPassword = new Password(passwordChangeRequest.getOldPassword());
        Password newPassword = new Password(passwordChangeRequest.getNewPassword());

        // when
        customerRepository.updatePassword(id, oldPassword, newPassword);

        // then
        assertThat(customerRepository.findById(id).getPassword())
                .isEqualTo(newPassword.get());
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        // given
        Long id = customerRepository.create(헌치);
        TokenRequest tokenRequest = new TokenRequest(id);

        // when
        customerRepository.delete(tokenRequest.getCustomerId());

        // then
        assertThatThrownBy(() -> customerRepository.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("존재하지 않는 데이터입니다.");
    }
}
